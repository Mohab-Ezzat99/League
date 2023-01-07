package mrandroid.league.presentation.team

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import mrandroid.league.R
import mrandroid.league.data.local.entity.TeamEntity
import mrandroid.league.databinding.FragmentTeamBinding
import mrandroid.league.presentation.BindingFragment
import mrandroid.league.presentation.dialog.LoadingDialog
import mrandroid.league.util.Constants.TAG
import mrandroid.league.util.showToast
import mrandroid.league.util.state.UiState
import javax.inject.Inject

@AndroidEntryPoint
class TeamFragment : BindingFragment<FragmentTeamBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentTeamBinding::inflate

    private val viewModel: TeamViewModel by viewModels()
    private val args: TeamFragmentArgs by navArgs()
    private val teamId get() = args.teamId

    @Inject
    lateinit var playerAdapter: PlayerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvPlayer.adapter = playerAdapter
        }

        viewModel.getTeamInfo(teamId)
        fetchTeamInfo()
    }

    private fun fetchTeamInfo() {
        lifecycleScope.launchWhenStarted {
            viewModel.teamState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        state.data?.let { competition ->
                            LoadingDialog.dismissDialog()
                            setupDetails(competition)
                        } ?: LoadingDialog.showDialog()
                    }
                    is UiState.Error -> {
                        LoadingDialog.dismissDialog()
                        state.message?.let { msg -> showToast(msg.asString(requireContext())) }
                    }
                    is UiState.Success -> {
                        LoadingDialog.dismissDialog()
                        setupDetails(state.data)
                        Log.d(TAG, state.data.toString())
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupDetails(data: TeamEntity?) {
        binding.apply {
            Log.d(TAG, data.toString())
            root.isVisible = true
            if (!data?.crestUrl.isNullOrEmpty())
                Glide.with(requireContext())
                    .load(data?.crestUrl)
                    .placeholder(R.drawable.ic_ball)
                    .into(ivImg)

            tvName.text = data?.name
            tvShortName.text = data?.tla
            tvAddress.text = data?.address
            tvWebsite.text = data?.website
            tvEmail.text = data?.email
            tvPhone.text = data?.phone
            tvFounded.text = data?.founded
            playerAdapter.submitList(data?.squad)
        }
    }
}
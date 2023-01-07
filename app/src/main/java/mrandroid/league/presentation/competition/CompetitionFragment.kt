package mrandroid.league.presentation.competition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import mrandroid.league.R
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.data.local.entity.WinnerEntity
import mrandroid.league.databinding.FragmentCompetitionBinding
import mrandroid.league.presentation.BindingFragment
import mrandroid.league.presentation.dialog.LoadingDialog
import mrandroid.league.presentation.team.TeamFragmentArgs
import mrandroid.league.util.Constants.TAG
import mrandroid.league.util.showToast
import mrandroid.league.util.state.UiState
import javax.inject.Inject

@AndroidEntryPoint
class CompetitionFragment : BindingFragment<FragmentCompetitionBinding>(),
    TeamAdapter.OnItemClickListener {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCompetitionBinding::inflate

    private val viewModel: CompetitionViewModel by viewModels()
    private val args: CompetitionFragmentArgs by navArgs()
    private val competitionId get() = args.competitionId

    @Inject
    lateinit var teamAdapter: TeamAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamAdapter.setListener(this)
        binding.apply {
            rvTeams.adapter = teamAdapter
        }

        viewModel.getCompetition(competitionId)
        fetchCompetitions()
    }

    private fun fetchCompetitions() {
        lifecycleScope.launchWhenStarted {
            viewModel.competitionState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        state.data?.let { competition ->
                            LoadingDialog.dismissDialog()
                            setupDetails(competition)
                        } ?: LoadingDialog.showDialog()
                    }
                    is UiState.Error -> {
                        LoadingDialog.dismissDialog()
                        state.message?.let { msg-> showToast(msg.asString(requireContext())) }
                    }
                    is UiState.Success -> {
                        LoadingDialog.dismissDialog()
                        setupDetails(state.data)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupDetails(data: CompetitionEntity?) {
        binding.apply {
            root.isVisible = true
            if (!data?.emblemUrl.isNullOrEmpty())
                Glide.with(requireContext())
                    .load(data?.emblemUrl)
                    .placeholder(R.drawable.ic_ball)
                    .into(ivImg)

            tvName.text = data?.name
            tvArea.text = data?.area?.name
            tvCode.text = data?.code
            tvPlan.text = data?.plan
            teamAdapter.submitList(data?.seasons?.mapNotNull { it.winner })
            Log.d(TAG, data?.seasons?.mapNotNull { it.winner }?.size.toString())
        }
    }

    override fun onCompetitionClick(model: WinnerEntity) {
        findNavController().navigate(R.id.teamFragment, TeamFragmentArgs(model.id).toBundle())
    }
}
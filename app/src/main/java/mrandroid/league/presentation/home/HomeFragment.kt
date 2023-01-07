package mrandroid.league.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import mrandroid.league.R
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.databinding.FragmentHomeBinding
import mrandroid.league.presentation.BindingFragment
import mrandroid.league.presentation.competition.CompetitionFragmentArgs
import mrandroid.league.presentation.dialog.LoadingDialog
import mrandroid.league.util.showToast
import mrandroid.league.util.state.UiState
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(),
    CompetitionAdapter.OnItemClickListener {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    @Inject
    lateinit var competitionAdapter: CompetitionAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        competitionAdapter.setListener(this)
        binding.apply {
            rvCompetitions.adapter = competitionAdapter
        }

        viewModel.getAllCompetitions()
        fetchCompetitions()
    }

    private fun fetchCompetitions() {
        lifecycleScope.launchWhenStarted {
            viewModel.competitionsState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        state.data?.let { list ->
                            LoadingDialog.dismissDialog()
                            competitionAdapter.submitList(list)
                        } ?: LoadingDialog.showDialog()
                    }
                    is UiState.Error -> {
                        LoadingDialog.dismissDialog()
                        state.message?.let { msg-> showToast(msg.asString(requireContext())) }
                    }
                    is UiState.Success -> {
                        LoadingDialog.dismissDialog()
                        competitionAdapter.submitList(state.data)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onCompetitionlick(model: CompetitionEntity) {
        findNavController().navigate(
            R.id.competitionFragment,
            CompetitionFragmentArgs(model.id).toBundle()
        )
    }
}
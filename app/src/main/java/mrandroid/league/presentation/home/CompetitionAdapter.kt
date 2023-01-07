package mrandroid.league.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.databinding.ItemCompetitionBinding
import javax.inject.Inject

class CompetitionAdapter @Inject constructor() :
    ListAdapter<CompetitionEntity, CompetitionAdapter.ProductViewHolder>(ITEM_COMPARATOR) {
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemCompetitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (currentList.isNotEmpty()) getItem(position).let { holder.bind(it) }
    }

    inner class ProductViewHolder(private val binding: ItemCompetitionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    getItem(adapterPosition).let {
                        listener.onCompetitionlick(it)
                    }
                }
            }
        }

        fun bind(model: CompetitionEntity) {
            binding.apply {
                val totalTeams = model.currentSeason?.let { season ->
                    season.currentMatchDay * 2
                } ?: 0
                val totalGames = model.currentSeason?.let { season ->
                    season.currentMatchDay * model.numberOfAvailableSeasons
                } ?: 0

                tvShortName.text = model.code
                tvLongName.text = model.name
                tvTeams.text = totalTeams.toString()
                tvGames.text = totalGames.toString()
            }
        }
    }

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onCompetitionlick(model: CompetitionEntity)
    }

    //check difference
    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<CompetitionEntity>() {
            override fun areItemsTheSame(
                oldItem: CompetitionEntity,
                newItem: CompetitionEntity
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CompetitionEntity,
                newItem: CompetitionEntity
            ) = oldItem == newItem
        }
    }
}
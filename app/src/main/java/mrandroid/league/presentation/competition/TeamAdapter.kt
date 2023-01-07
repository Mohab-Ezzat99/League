package mrandroid.league.presentation.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mrandroid.league.R
import mrandroid.league.data.local.entity.WinnerEntity
import mrandroid.league.databinding.ItemTeamBinding
import javax.inject.Inject

class TeamAdapter @Inject constructor() :
    ListAdapter<WinnerEntity, TeamAdapter.ProductViewHolder>(ITEM_COMPARATOR) {
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (currentList.isNotEmpty()) getItem(position).let { holder.bind(it) }
    }

    inner class ProductViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    getItem(adapterPosition).let {
                        listener.onCompetitionClick(it)
                    }
                }
            }
        }

        fun bind(model: WinnerEntity) {
            binding.apply {
                tvShortName.text = model.tla
                tvLongName.text = model.name
                if (model.crestUrl.isNotEmpty())
                    Glide.with(itemView)
                        .load(model.crestUrl)
                        .placeholder(R.drawable.ic_ball)
                        .into(this.ivTeamImg)
            }
        }
    }

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onCompetitionClick(model: WinnerEntity)
    }

    //check difference
    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<WinnerEntity>() {
            override fun areItemsTheSame(
                oldItem: WinnerEntity,
                newItem: WinnerEntity
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WinnerEntity,
                newItem: WinnerEntity
            ) = oldItem == newItem
        }
    }
}
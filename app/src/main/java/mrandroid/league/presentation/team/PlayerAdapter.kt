package mrandroid.league.presentation.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mrandroid.league.data.local.entity.SquadEntity
import mrandroid.league.databinding.ItemPlayerBinding
import javax.inject.Inject

class PlayerAdapter @Inject constructor() :
    ListAdapter<SquadEntity, PlayerAdapter.ProductViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (currentList.isNotEmpty()) getItem(position).let { holder.bind(it) }
    }

    inner class ProductViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SquadEntity) {
            binding.apply {
                tvName.text = model.name
                tvNationality.text = model.nationality
                tvPosition.text = model.position
            }
        }
    }

    //check difference
    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<SquadEntity>() {
            override fun areItemsTheSame(
                oldItem: SquadEntity,
                newItem: SquadEntity
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SquadEntity,
                newItem: SquadEntity
            ) = oldItem == newItem
        }
    }
}
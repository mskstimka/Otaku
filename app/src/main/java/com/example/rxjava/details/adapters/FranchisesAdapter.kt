package com.example.rxjava.details.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.NOT_FOUND_TEXT
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.rxjava.databinding.ItemDetailsFranchisesBinding
import com.example.rxjava.details.ui.DetailsFragmentDirections
import com.example.rxjava.utils.setImageByURL

class FranchisesAdapter :
    ListAdapter<AnimeDetailsFranchisesEntity, FranchisesAdapter.FranchisesViewHolder>(
        FranchisesDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FranchisesViewHolder {
        val binding =
            ItemDetailsFranchisesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FranchisesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FranchisesViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class FranchisesViewHolder(
        private val binding:
        ItemDetailsFranchisesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsFranchisesEntity) = with(binding) {

            ivImageFranchisesItem.setImageByURL(
                model.image_url.replace(
                    oldValue = "x96",
                    newValue = "original"
                )
            )
            tvTitleFranchisesItem.text = model.name
            tvKindDateFranchisesItem.text = "${model.kind} / ${model.year}"

            itemView.setOnClickListener {
                if (model.kind != NOT_FOUND_TEXT)
                    itemView.findNavController().navigate(
                        DetailsFragmentDirections.actionDetailsFragmentSelf(model.id)
                    )
            }
        }
    }

    object FranchisesDiffCallback : DiffUtil.ItemCallback<AnimeDetailsFranchisesEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsFranchisesEntity,
            newItem: AnimeDetailsFranchisesEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsFranchisesEntity,
            newItem: AnimeDetailsFranchisesEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}

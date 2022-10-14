package com.example.otaku.details.info.adapters.franchises

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.NOT_FOUND_TEXT
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.otaku.databinding.ItemDetailsFranchisesBinding
import com.example.otaku.utils.setImageByURL

class FranchisesAdapter(private val actionToFranchises: (id : Int) -> Unit) :
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
                model.image_url?.replace(
                    oldValue = "x96",
                    newValue = "original"
                ) ?: NOT_FOUND_TEXT
            )

            tvTitleFranchisesItem.text = model.name
            tvKindDateFranchisesItem.text = if (model.year == null) {
                "${model.kind}"
            } else {
                "${model.kind} / ${model.year}"
            }

            itemView.setOnClickListener {
                if (model.kind != NOT_FOUND_TEXT) {
                    actionToFranchises(model.id)
                }
            }
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


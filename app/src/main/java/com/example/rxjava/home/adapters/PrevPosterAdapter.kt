package com.example.rxjava.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemPrevPosterBinding
import com.example.rxjava.home.ui.HomeFragmentDirections
import com.squareup.picasso.Picasso

class PrevPosterAdapter :
    ListAdapter<AnimePosterEntity, PrevPosterAdapter.PrevPosterViewHolder>(
        PrevPosterDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevPosterViewHolder {
        val binding =
            ItemPrevPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrevPosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrevPosterViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class PrevPosterViewHolder(
        private val binding:
        ItemPrevPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimePosterEntity) = with(binding) {

            Picasso.get().load(Constants.SHIKIMORI_URL + model.image.original)
                .error(R.drawable.icon_default)
                .into(ivImageFranchisesItem)
            tvTitleFranchisesItem.text = model.name
            tvKindDateFranchisesItem.text = "Episodes ${model.episodes}"

            itemView.setOnClickListener{
                itemView.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(model.id)
                )
            }
        }
    }


    object PrevPosterDiffCallback : DiffUtil.ItemCallback<AnimePosterEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimePosterEntity,
            newItem: AnimePosterEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimePosterEntity,
            newItem: AnimePosterEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}


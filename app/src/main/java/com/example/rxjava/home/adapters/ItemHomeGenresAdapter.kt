package com.example.rxjava.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemHomeGenresItemBinding
import com.example.rxjava.home.ui.HomeFragmentDirections
import com.squareup.picasso.Picasso

class ItemHomeGenresAdapter :
    ListAdapter<AnimePosterEntity, ItemHomeGenresAdapter.PrevPosterViewHolder>(
        PrevPosterDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevPosterViewHolder {
        val binding =
            ItemHomeGenresItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrevPosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrevPosterViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class PrevPosterViewHolder(
        private val binding:
        ItemHomeGenresItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimePosterEntity) = with(binding) {

            Picasso.get().load(Constants.SHIKIMORI_URL + model.image.original)
                .error(R.drawable.icon_default)
                .into(ivImageHomeGenre)

            tvTitleHomeGenre.text = if (model.russian == "") {
                model.name
            } else {
                model.russian
            }
            tvEpisodesHomeGenre.text = "Episodes ${model.episodes}"

            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(model.id)
                )
            }

            tvEpisodesHomeGenre.text = if (model.episodes.toString() != "0") {
                "Episodes: ${model.episodes}"
            } else {
                "Episodes: ${model.episodesAired}"
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


package com.example.rxjava.home.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.SHIKIMORI_URL
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemHomeGenresItemBinding
import com.example.rxjava.home.ui.HomeFragmentDirections
import com.example.rxjava.utils.setImageByURL

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

        @SuppressLint("SetTextI18n", "StringFormatMatches")
        fun bind(model: AnimePosterEntity) = with(binding) {

            ivImageHomeGenre.setImageByURL(SHIKIMORI_URL + model.image.original)

            tvTitleHomeGenre.text = if (model.russian == "") {
                model.name
            } else {
                model.russian
            }

            tvEpisodesHomeGenre.text = if (model.episodes.toString() != "0") {
               root.context.getString(R.string.episode_text, model.episodes)
            } else {
                root.context.getString(R.string.episode_text, model.episodesAired)
            }

            itemView.setOnClickListener {
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


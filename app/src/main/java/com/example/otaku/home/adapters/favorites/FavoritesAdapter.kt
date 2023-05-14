package com.example.otaku.home.adapters.favorites

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.anime.AnimeFragmentDirections
import com.example.otaku.databinding.ItemFavoritesPostersBinding
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.poster.AnimePosterEntity


class FavoritesAdapter :
    ListAdapter<AnimePosterEntity, FavoritesAdapter.FavoritesViewHolder>(
        FavoritesDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            ItemFavoritesPostersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class FavoritesViewHolder(
        private val binding:
        ItemFavoritesPostersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("StringFormatMatches")
        fun bind(model: AnimePosterEntity) = with(binding) {

            ivImageHomeGenre.setImageByURL(SHIKIMORI_URL + model.image.original)

            tvTitleHomeGenre.text = if (model.russian == "") {
                model.name
            } else {
                model.russian
            }

            tvKind.text = model.status
            tvKind.setTextColor(Color.parseColor(model.statusColor))

            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    AnimeFragmentDirections.actionAnimeFragmentToDetailsFragment(model.id)
                )
            }
        }
    }


    object FavoritesDiffCallback : DiffUtil.ItemCallback<AnimePosterEntity>() {
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
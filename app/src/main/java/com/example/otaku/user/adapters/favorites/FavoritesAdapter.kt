package com.example.otaku.user.adapters.favorites

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku_domain.models.user.Favorite
import com.example.otaku_domain.models.user.FavoriteType
import com.example.otaku.databinding.ItemDetailsFranchisesBinding
import com.example.otaku.user.ui.UserFragmentDirections
import com.example.otaku.utils.setImageByURL

class FavoritesAdapter : ListAdapter<Favorite, FavoritesAdapter.FavoriteViewHolder>(
    FavoriteDiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding =
            ItemDetailsFranchisesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class FavoriteViewHolder(
        private val binding:
        ItemDetailsFranchisesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(favorite: Favorite) = with(binding) {

            ivImageFranchisesItem.setImageByURL(favorite.image)
            tvTitleFranchisesItem.text = favorite.name
            tvKindDateFranchisesItem.text = favorite.nameRu ?: ""

            itemView.setOnClickListener {
                when (favorite.type) {
                    FavoriteType.ANIME -> {
                        root.findNavController().navigate(
                            UserFragmentDirections.actionUserFragmentToDetailsFragment(favorite.id.toInt())
                        )
                    }
                    FavoriteType.CHARACTERS -> {
                        root.findNavController().navigate(
                            UserFragmentDirections.actionUserFragmentToCharactersFragment(favorite.id.toInt())
                        )

                    }
                    FavoriteType.MANGAKAS, FavoriteType.PRODUCERS, FavoriteType.SEYU, FavoriteType.PEOPLE -> {
                        root.findNavController().navigate(
                            UserFragmentDirections.actionUserFragmentToPersonFragment(favorite.id.toInt())
                        )

                    }
                    FavoriteType.MANGA -> {

                    }
                }

            }
        }
    }


    object FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(
            oldItem: Favorite,
            newItem: Favorite
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Favorite,
            newItem: Favorite
        ): Boolean {
            return oldItem == newItem
        }
    }
}
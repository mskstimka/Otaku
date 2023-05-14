package com.example.otaku.user.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutUserFavoritesBinding
import com.example.otaku_domain.models.user.FavoriteList

class UserFavoritesAdapter :
    ListAdapter<FavoriteList, UserFavoritesAdapter.UserFavoritesViewHolder>(
        UserFavoritesInfoDiffCallback
    ) {

    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFavoritesViewHolder {
        val binding =
            LayoutUserFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvFavorites.adapter = favoritesAdapter

        return UserFavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavoritesViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<FavoriteList>?) {
        if (list?.first()?.all != null) {
            favoritesAdapter.submitList(list.first().all)
        }
        super.submitList(list)
    }

    inner class UserFavoritesViewHolder(
        private val binding:
        LayoutUserFavoritesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FavoriteList) = with(binding) {

        }
    }

    object UserFavoritesInfoDiffCallback : DiffUtil.ItemCallback<FavoriteList>() {
        override fun areItemsTheSame(
            oldItem: FavoriteList,
            newItem: FavoriteList
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: FavoriteList,
            newItem: FavoriteList
        ): Boolean {
            return oldItem == newItem
        }
    }

}
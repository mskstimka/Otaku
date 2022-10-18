package com.example.otaku.home.adapters.favorites

import com.example.otaku.databinding.LayoutFavoritesBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ContainerFavoritesAdapter :
    ListAdapter<ContainerFavorites, ContainerFavoritesAdapter.ContainerFavoritesViewHolder>(
        ContainerFavoritesDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerFavoritesViewHolder {
        val binding =
            LayoutFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val favoritesAdapter by lazy { FavoritesAdapter() }

        binding.rvGenreList.adapter = favoritesAdapter
        binding.rvGenreList.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)

        return ContainerFavoritesViewHolder(binding, favoritesAdapter)
    }

    override fun onBindViewHolder(holder: ContainerFavoritesViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerFavoritesViewHolder(
        private val binding:
        LayoutFavoritesBinding, private val adapter: FavoritesAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerFavorites) = with(binding) {
            adapter.submitList(model.list)
        }
    }


    object ContainerFavoritesDiffCallback : DiffUtil.ItemCallback<ContainerFavorites>() {
        override fun areItemsTheSame(
            oldItem: ContainerFavorites,
            newItem: ContainerFavorites
        ): Boolean {
            return oldItem.id.equals(newItem.id, true)
        }

        override fun areContentsTheSame(
            oldItem: ContainerFavorites,
            newItem: ContainerFavorites
        ): Boolean {
            return oldItem == newItem
        }
    }
}
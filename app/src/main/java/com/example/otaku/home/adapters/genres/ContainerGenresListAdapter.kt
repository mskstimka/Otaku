package com.example.otaku.home.adapters.genres

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.ItemHomeGenresListBinding

class ContainerGenresListAdapter :
    ListAdapter<ContainerGenresList, ContainerGenresListAdapter.ContainerGenresListViewHolder>(
        ContainerGenresListDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerGenresListViewHolder {
        val binding =
            ItemHomeGenresListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val genresListAdapter by lazy { ItemHomeGenresAdapter() }

        binding.rvGenreList.adapter = genresListAdapter
        binding.rvGenreList.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)

        return ContainerGenresListViewHolder(binding, genresListAdapter)
    }

    override fun onBindViewHolder(holder: ContainerGenresListViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class ContainerGenresListViewHolder(
        private val binding:
        ItemHomeGenresListBinding, private val adapter: ItemHomeGenresAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerGenresList) = with(binding) {
            tvGenreTitle.text = model.title
            adapter.submitList(model.list)
        }
    }


    object ContainerGenresListDiffCallback : DiffUtil.ItemCallback<ContainerGenresList>() {
        override fun areItemsTheSame(
            oldItem: ContainerGenresList,
            newItem: ContainerGenresList
        ): Boolean {
            Log.d(oldItem.id.equals(newItem.id, true).toString(),"CHECK ---------------")
            return oldItem.id.equals(newItem.id, true)
        }

        override fun areContentsTheSame(
            oldItem: ContainerGenresList,
            newItem: ContainerGenresList
        ): Boolean {
            return oldItem == newItem
        }
    }
}
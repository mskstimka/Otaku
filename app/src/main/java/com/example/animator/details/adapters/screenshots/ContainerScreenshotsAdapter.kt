package com.example.animator.details.adapters.screenshots

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.LayoutScreenshotsInfoBinding

class ContainerScreenshotsAdapter :
    ListAdapter<ContainerScreenshots, ContainerScreenshotsAdapter.ParentScreenshotsViewHolder>(
        ParentScreenshotsDiffCallback
    ) {

    private val screenshotsAdapter by lazy { ScreenshotsAdapter() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentScreenshotsViewHolder {
        val binding =
            LayoutScreenshotsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvScreenshots.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvScreenshots.adapter = screenshotsAdapter

        return ParentScreenshotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentScreenshotsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentScreenshotsViewHolder(
        private val binding:
        LayoutScreenshotsInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ContainerScreenshots) = with(binding) {

            screenshotsAdapter.submitList(item.list)
        }
    }


    object ParentScreenshotsDiffCallback : DiffUtil.ItemCallback<ContainerScreenshots>() {
        override fun areItemsTheSame(
            oldItem: ContainerScreenshots,
            newItem: ContainerScreenshots
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerScreenshots,
            newItem: ContainerScreenshots
        ): Boolean {
            return oldItem == newItem
        }
    }
}
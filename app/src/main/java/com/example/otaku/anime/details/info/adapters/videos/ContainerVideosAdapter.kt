package com.example.otaku.anime.details.info.adapters.videos

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutDetailsVideosInfoBinding

class ContainerVideosAdapter(private val openActivity: (intent: Intent) -> Unit) :
    ListAdapter<ContainerVideos, ContainerVideosAdapter.ParentVideosViewHolder>(
        ParentVideosDiffCallback
    ) {

    private val videosAdapter by lazy { VideosAdapter(openActivity) }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentVideosViewHolder {
        val binding =
            LayoutDetailsVideosInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvVideos.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvVideos.adapter = videosAdapter

        return ParentVideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentVideosViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentVideosViewHolder(
        private val binding:
        LayoutDetailsVideosInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerVideos) = with(binding) {

            videosAdapter.submitList(item.list)
        }
    }


    object ParentVideosDiffCallback : DiffUtil.ItemCallback<ContainerVideos>() {
        override fun areItemsTheSame(
            oldItem: ContainerVideos,
            newItem: ContainerVideos
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerVideos,
            newItem: ContainerVideos
        ): Boolean {
            return oldItem == newItem
        }
    }
}

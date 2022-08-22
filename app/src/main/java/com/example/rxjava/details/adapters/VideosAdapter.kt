package com.example.rxjava.details.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.Video
import com.example.rxjava.databinding.ItemDetailsVideosBinding
import com.example.rxjava.utils.setImageByURL

class VideosAdapter :
    ListAdapter<Video, VideosAdapter.VideosViewHolder>(
        VideosDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding =
            ItemDetailsVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class VideosViewHolder(
        private val binding: ItemDetailsVideosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Video) = with(binding) {

            ivImageVideosItem.setImageByURL(model.image_url.replace("http", "https"))
            tvTitleVideosItem.text = model.name
            tvHostingVideosItem.text = model.hosting
        }
    }

    object VideosDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return oldItem == newItem
        }
    }
}


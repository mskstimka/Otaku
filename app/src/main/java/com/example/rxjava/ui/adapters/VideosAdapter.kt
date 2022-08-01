package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.Video
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemVideosBinding
import com.squareup.picasso.Picasso

class VideosAdapter(context: Context) :
    ListAdapter<Video, VideosAdapter.VideosViewHolder>(
        VideosDiffCallback
    ) {

    private val message = context.getString(R.string.not_found)
    private val defaultVideo = Video(message, 0, "http", message, message, message, message)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding =
            ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class VideosViewHolder(
        private val binding: ItemVideosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Video) = with(binding) {
            Picasso.get().load(model.image_url.replace("http", "https"))
                .error(R.drawable.icon_default).into(ivImageVideosItem)
            tvTitleVideosItem.text = model.name
            tvHostingVideosItem.text = model.hosting
        }
    }

    override fun submitList(list: List<Video>?) {
        val videosList = when (list) {
            emptyList<Video>() -> listOf(defaultVideo)
            else -> list
        }

        super.submitList(videosList)
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


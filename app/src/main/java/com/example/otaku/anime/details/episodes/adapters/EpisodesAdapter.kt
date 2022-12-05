package com.example.otaku.anime.details.episodes.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.ItemEpisodesBinding
import com.example.otaku.databinding.ItemEpisodesHeaderBinding
import com.example.otaku.anime.details.episodes.models.ContainerEpisodeHeader
import com.example.otaku.anime.details.episodes.models.ContainerEpisodes
import com.example.otaku.anime.details.episodes.models.DisplayableItem

class EpisodesAdapter(
    private val actionSearch: (Int) -> Unit,
    private val onBackPressed: () -> Unit
) :
    ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(
        EpisodesDiffCallback
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ContainerEpisodes -> EPISODES_TYPE
            is ContainerEpisodeHeader -> HEADER_TYPE
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            EPISODES_TYPE -> EpisodesViewHolder(
                ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            HEADER_TYPE -> HeaderViewHolder(
                ItemEpisodesHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = currentList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(element as ContainerEpisodeHeader)
            is EpisodesViewHolder -> holder.bind(element as ContainerEpisodes)
            else -> throw IllegalArgumentException()

        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class EpisodesViewHolder(
        private val binding:
        ItemEpisodesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerEpisodes) = with(binding) {

            root.setOnClickListener {
                actionSearch(item.episode)
            }

            root.text = root.context.getString(
                R.string.fragment_episodes_item_text,
                item.episode.toString()
            )
        }
    }


    inner class HeaderViewHolder(
        private val binding:
        ItemEpisodesHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerEpisodeHeader) = with(binding) {

            ivBackPressed.setOnClickListener {
                onBackPressed()
            }

            episodesTitle.setOnClickListener {
                item.action()
            }
        }
    }

    object EpisodesDiffCallback : DiffUtil.ItemCallback<DisplayableItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean {

            return if (oldItem is ContainerEpisodes && newItem is ContainerEpisodes) {
                oldItem.episode == newItem.episode
            } else if (oldItem is ContainerEpisodeHeader && newItem is ContainerEpisodeHeader) {
                oldItem.title == newItem.title
            } else {
                oldItem.id == oldItem.id
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val HEADER_TYPE = 1
        const val EPISODES_TYPE = 2
    }
}
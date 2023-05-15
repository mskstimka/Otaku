package com.example.otaku.user.rates.anime

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.ItemSearchPostersBinding
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.poster.AnimePosterEntity

class AnimeRatesAdapter :
    PagingDataAdapter<AnimePosterEntity, AnimeRatesAdapter.TitleViewHolder>(PosterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding =
            ItemSearchPostersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class TitleViewHolder(
        private val binding: ItemSearchPostersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("StringFormatMatches")
        fun bind(model: AnimePosterEntity?) = with(binding) {
            if (model != null) {
                tvSearchPosterName.text = model.name
                tvSearchPosterScore.text = model.score

                tvSearchPosterEpisodes.text = if (model.episodes.toString() != "0") {
                    root.context.getString(R.string.episode_text, model.episodes)
                } else {
                    root.context.getString(R.string.episode_text, model.episodesAired)
                }

                tvSearchPosterRussianName.text = model.russian
                tvSearchPosterStatus.text = model.status
                tvSearchPosterStatus.setTextColor(Color.parseColor(model.statusColor))
                ivSearchPosterImage.setImageByURL(SHIKIMORI_URL + model.image.original)

                itemView.setOnClickListener {
                    itemView.findNavController().navigate(
                        AnimeRatesFragmentDirections.actionAnimeRatesFragmentToDetailsFragment(
                            id = model.id
                        )
                    )
                }
            }
        }
    }


    object PosterDiffCallback : DiffUtil.ItemCallback<AnimePosterEntity>() {
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
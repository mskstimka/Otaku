package com.example.otaku.anime.details.info.adapters.screenshots

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.otaku.R
import com.example.otaku.anime.AnimeFragmentDirections
import com.example.otaku.anime.details.info.ui.DetailsFragmentDirections
import com.example.otaku.databinding.ItemDetailsScreenshotsBinding
import com.example.otaku.utils.setImageByURL

class ScreenshotsAdapter :
    ListAdapter<AnimeDetailsScreenshotsEntity, ScreenshotsAdapter.ScreenshotsViewHolder>(
        ScreenshotsDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val binding =
            ItemDetailsScreenshotsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ScreenshotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class ScreenshotsViewHolder(
        private val binding: ItemDetailsScreenshotsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsScreenshotsEntity) = with(binding) {

            ivImageScreenshotsItem.setImageByURL(SHIKIMORI_URL + model.original)

            ivImageScreenshotsItem.setOnClickListener {
                when (root.findNavController().currentDestination?.id) {
                    R.id.detailsFragment -> {
                        root.findNavController().navigate(
                            DetailsFragmentDirections.actionDetailsFragmentToScreenshotsFragment(
                                SHIKIMORI_URL + model.original
                            )
                        )
                    }
                    R.id.animeFragment -> {
                        root.findNavController().navigate(
                            AnimeFragmentDirections.actionAnimeFragmentToScreenshotsFragment(
                                SHIKIMORI_URL + model.original
                            )
                        )
                    }
                }

            }

        }
    }

    object ScreenshotsDiffCallback : DiffUtil.ItemCallback<AnimeDetailsScreenshotsEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsScreenshotsEntity,
            newItem: AnimeDetailsScreenshotsEntity
        ): Boolean {
            return oldItem.original == newItem.original
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsScreenshotsEntity,
            newItem: AnimeDetailsScreenshotsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}

package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemScreenshotsBinding
import com.example.a16_rxjava_domain.models.Constants
import com.squareup.picasso.Picasso

class ScreenshotsAdapter(context: Context) :
    ListAdapter<AnimeDetailsScreenshotsEntity, ScreenshotsAdapter.ScreenshotsViewHolder>(
        ScreenshotsDiffCallback
    ) {

    private val message = context.getString(R.string.not_found)
    private val defaultScreenshot = AnimeDetailsScreenshotsEntity(message, message)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val binding =
            ItemScreenshotsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScreenshotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ScreenshotsViewHolder(
        private val binding: ItemScreenshotsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsScreenshotsEntity) = with(binding) {

            Picasso.get().load(Constants.SHIKIMORI_URL + model.original)
                .error(R.drawable.icon_default).into(ivImageScreenshotsItem)

        }
    }

    override fun submitList(list: List<AnimeDetailsScreenshotsEntity>?) {
        val screenshotsList = when (list) {
            emptyList<AnimeDetailsScreenshotsEntity>() -> listOf(defaultScreenshot)
            else -> list
        }

        super.submitList(screenshotsList)
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

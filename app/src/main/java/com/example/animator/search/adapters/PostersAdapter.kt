package com.example.animator.search.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator.R
import com.example.animator.databinding.ItemSearchPostersBinding
import com.example.animator.utils.setImageByURL

class PostersAdapter(private val callbackClick: (posterId: Int) -> Unit) :
    ListAdapter<AnimePosterEntity, PostersAdapter.TitleViewHolder>(PosterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding =
            ItemSearchPostersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class TitleViewHolder(
        private val binding: ItemSearchPostersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor", "StringFormatMatches")
        fun bind(model: AnimePosterEntity) = with(binding) {
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
                callbackClick.invoke(model.id)
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
package com.example.rxjava.search.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemSearchPostersBinding
import com.squareup.picasso.Picasso

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

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(model: AnimePosterEntity) = with(binding) {
            tvSearchPosterName.text = model.name
            tvSearchPosterScore.text = model.score

            tvSearchPosterEpisodes.text = if (model.episodes.toString() != "0") {
                "Episodes: ${model.episodes}"
            } else {
                "Episodes: ${model.episodesAired}"
            }
            tvSearchPosterRussianName.text = model.russian
            tvSearchPosterStatus.text = model.status
            when (tvSearchPosterStatus.text) {
                Constants.ONGOING_STATUS -> tvSearchPosterStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.blue_status
                    )
                )
                Constants.ANONS_STATUS -> tvSearchPosterStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.red_status
                    )
                )
                Constants.RELEASED_STATUS -> tvSearchPosterStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.green_status
                    )
                )
            }
            Picasso.get().load(Constants.SHIKIMORI_URL + model.image.original)
                .error(R.drawable.icon_default).into(ivSearchPosterImage)

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
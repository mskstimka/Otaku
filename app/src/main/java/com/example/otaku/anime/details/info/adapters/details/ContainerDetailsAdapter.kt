package com.example.otaku.anime.details.info.adapters.details

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.NOT_FOUND_TEXT
import com.example.domain.SHIKIMORI_URL
import com.example.domain.models.details.AnimeDetailsEntity
import com.example.domain.models.poster.AnimePosterEntity
import com.example.otaku.R
import com.example.otaku.anime.details.info.ui.DetailsFragment
import com.example.otaku.databinding.LayoutDetailsInfoBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.copyToClipboard
import com.example.otaku.utils.setImageByURL

class ContainerDetailsAdapter(
    private val onBackPressed: () -> Unit,
    private val addFavorites: (id: AnimePosterEntity) -> Unit,
    private val deleteFavorites: (id: Int) -> Unit,
    private val checkIsFavorite: (id: Int) -> Boolean
) :
    ListAdapter<AnimeDetailsEntity, ContainerDetailsAdapter.ParentDetailsViewHolder>(
        ParentDetailsDiffCallback
    ) {

    private val genresAdapter by lazy { GenresAdapter() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentDetailsViewHolder {
        val binding =
            LayoutDetailsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        with(binding) {
            rvGenre.adapter = genresAdapter
            rvGenre.layoutManager =
                LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)

            ivBackPressed.setOnClickListener {
                onBackPressed()
            }
        }

        return ParentDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentDetailsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentDetailsViewHolder(
        private val binding:
        LayoutDetailsInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AnimeDetailsEntity) = with(binding) {

            val poster = AnimePosterEntity(
                id = item.id,
                image = item.image,
                name = item.name ?: NOT_FOUND_TEXT,
                score = item.score ?: "0.0",
                episodes = item.episodes ?: 0,
                episodesAired = item.episodes_aired ?: 0,
                url = NOT_FOUND_TEXT,
                status = item.status ?: NOT_FOUND_TEXT,
                statusColor = "",
                russian = item.russian ?: ""
            )

            ivImageFranchises.setImageByURL(SHIKIMORI_URL + item.image.original)
            ivImageBackground.setImageByURL(SHIKIMORI_URL + item.image.original)

            tvTitle.text = item.name
            tvTitleRussian.text = item.russian

            exDescription.text = if (item.description != null) {
                HtmlCompat.fromHtml(item.description_html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                NOT_FOUND_TEXT
            }

            tvScore.text = item.score
            tvKind.text = item.kind

            tvEpisode.text = if (item.episodes.toString() != DetailsFragment.ZERO_TEXT) {
                item.episodes.toString()
            } else {
                item.episodes_aired.toString()
            }

            tvDate.text = item.aired_on
            tvStatus.text = item.status
            tvStatus.setTextColor(Color.parseColor(item.statusColor))

            if (checkIsFavorite(item.id)) {
                addIsFavorite(tvFavorite, root.context, poster, false)
            } else {
                deleteIsFavorite(tvFavorite, root.context, item.id, false)
            }


            tvFavorite.setOnClickListener {
                if (checkIsFavorite(item.id)) {
                    deleteIsFavorite(tvFavorite, root.context, item.id, true)
                } else {
                    addIsFavorite(
                        tvFavorite,
                        root.context,
                        poster,
                        true
                    )
                }
            }

            tvTitle.setOnLongClickListener(View.OnLongClickListener {
                root.context.copyToClipboard(tvTitle.text)

                return@OnLongClickListener false
            })

            genresAdapter.submitList(item.genres)
        }
    }

    private fun addIsFavorite(
        view: TextView,
        context: Context,
        item: AnimePosterEntity,
        isLocal: Boolean
    ) {
        view.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.icon_favorite_true,
                0,
                0,
                0
            )
            text = context.getString(R.string.is_favorites)
        }
        if (isLocal) {
            addFavorites(item)
        }
    }

    private fun deleteIsFavorite(view: TextView, context: Context, id: Int, isLocal: Boolean) {
        view.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.icon_favorite_false,
                0,
                0,
                0
            )
            text = context.getString(R.string.is_not_favorites)
        }
        if (isLocal) {
            deleteFavorites(id)
        }
    }

    object ParentDetailsDiffCallback : DiffUtil.ItemCallback<AnimeDetailsEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsEntity,
            newItem: AnimeDetailsEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsEntity,
            newItem: AnimeDetailsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
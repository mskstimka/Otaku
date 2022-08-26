package com.example.animator.details.adapters.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.LayoutDetailsInfoBinding
import com.example.animator.details.ui.DetailsFragment
import com.example.animator.utils.setImageByURL
import com.example.animator_domain.NOT_FOUND_TEXT
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.details.AnimeDetailsEntity

class ContainerDetailsAdapter(private val onBackPressed: () -> Unit) :
    ListAdapter<AnimeDetailsEntity, ContainerDetailsAdapter.ParentDetailsViewHolder>(
        ParentDetailsDiffCallback
    ) {

    private val genresAdapter by lazy { GenresAdapter() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentDetailsViewHolder {
        val binding =
            LayoutDetailsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvGenre.adapter = genresAdapter
        binding.rvGenre.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)

        binding.ivBackPressed.setOnClickListener {
            onBackPressed()
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

            genresAdapter.submitList(item.genres)

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
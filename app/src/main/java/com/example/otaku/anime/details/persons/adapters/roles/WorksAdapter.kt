package com.example.otaku.anime.details.persons.adapters.roles

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.ItemPersonRolesBinding
import com.example.otaku.utils.setImageByURL
import com.example.animator_domain.NOT_FOUND_TEXT
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.WorkEntity

class WorksAdapter(private val actionToDetails: (id: Int) -> Unit) :
    ListAdapter<WorkEntity, WorksAdapter.WorksViewHolder>(
        WorksDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorksViewHolder {
        val binding =
            ItemPersonRolesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WorksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class WorksViewHolder(
        private val binding:
        ItemPersonRolesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("StringFormatMatches")
        fun bind(model: WorkEntity) = with(binding) {

            with(model) {
                tvRole.text = role
                tvName.text = anime.name
                tvRussian.text = anime.russian
                tvEpisodes.text = if (anime.episodes.toString() != "0") {
                    root.context.getString(R.string.episode_text, anime.episodes)
                } else {
                    root.context.getString(R.string.episode_text, anime.episodesAired)
                }
                tvStatus.text = anime.status
                tvStatus.setTextColor(Color.parseColor(anime.statusColor))
                ivImage.setImageByURL(SHIKIMORI_URL + anime.image.original)

                itemView.setOnClickListener {
                    if (anime.name != NOT_FOUND_TEXT) {
                        actionToDetails(anime.id)
                    }
                }
            }


        }
    }


    object WorksDiffCallback : DiffUtil.ItemCallback<WorkEntity>() {
        override fun areItemsTheSame(
            oldItem: WorkEntity,
            newItem: WorkEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WorkEntity,
            newItem: WorkEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}

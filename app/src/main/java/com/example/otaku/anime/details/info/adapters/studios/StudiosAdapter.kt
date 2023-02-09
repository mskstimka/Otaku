package com.example.otaku.anime.details.info.adapters.studios

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.SHIKIMORI_URL
import com.example.domain.models.details.Studio
import com.example.otaku.databinding.ItemDetailsStudiosBinding
import com.example.otaku.utils.setImageStudioByURL

class StudiosAdapter :
    ListAdapter<Studio, StudiosAdapter.StudiosViewHolder>(
        CharactersDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudiosViewHolder {
        val binding =
            ItemDetailsStudiosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudiosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudiosViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class StudiosViewHolder(
        private val binding:
        ItemDetailsStudiosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Studio) = with(binding) {

            ivImageStudioItem.setImageStudioByURL(SHIKIMORI_URL + model.image)

        }
    }

    object CharactersDiffCallback : DiffUtil.ItemCallback<Studio>() {
        override fun areItemsTheSame(
            oldItem: Studio,
            newItem: Studio
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Studio,
            newItem: Studio
        ): Boolean {
            return oldItem == newItem
        }
    }
}



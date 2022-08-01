package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.Studio
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemStudiosBinding
import com.example.a16_rxjava_domain.models.Constants
import com.squareup.picasso.Picasso

class StudiosAdapter(context: Context) :
    ListAdapter<Studio, StudiosAdapter.StudiosViewHolder>(
        CharactersDiffCallback
    ) {

    private val message = context.getString(R.string.not_found)
    private val defaultStudio = Studio(message, 404, message, message, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudiosViewHolder {
        val binding =
            ItemStudiosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudiosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudiosViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class StudiosViewHolder(
        private val binding:
        ItemStudiosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Studio) = with(binding) {

            Picasso.get().load(Constants.SHIKIMORI_URL + model.image)
                .error(R.drawable.icon_studio_default).into(ivImageStudioItem)

        }
    }

    override fun submitList(list: List<Studio>?) {


        val studiosList = when (list) {
            emptyList<Studio>() -> listOf(defaultStudio)
            else -> list
        }
        super.submitList(studiosList)
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



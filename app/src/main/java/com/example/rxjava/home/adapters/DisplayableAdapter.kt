package com.example.rxjava.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.databinding.AdvertisingItemsBinding
import com.example.rxjava.databinding.PostItemsBinding
import com.example.rxjava.databinding.TitleItemsBinding
import java.lang.IllegalArgumentException

class DisplayableAdapter :
    ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(DisplayableDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder(
                TitleItemsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            POSTER_TYPE -> PrevPostersViewHolder(
                PostItemsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ADVERTISING_TYPE -> AdvertisingViewHolder(
                AdvertisingItemsBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val element = currentList[position]
        when (holder) {
            is TitleViewHolder -> holder.bind(element as PrevTitle)
            is PrevPostersViewHolder -> holder.bind(element as PrevPoster)
            is AdvertisingViewHolder -> holder.bind(element as PrevAdvertising)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is PrevTitle -> TITLE_TYPE
            is PrevPoster -> POSTER_TYPE
            is PrevAdvertising -> ADVERTISING_TYPE
            else -> throw IllegalArgumentException()
        }
    }

    inner class AdvertisingViewHolder(
        private val binding: AdvertisingItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PrevAdvertising) = with(binding) {
        }
    }

    inner class TitleViewHolder(
        private val binding: TitleItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PrevTitle) = with(binding) {

            tvGenreTitle.text = model.title
        }
    }


    inner class PrevPostersViewHolder(
        private val binding: PostItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PrevPoster) = with(binding) {
            val adapter = PrevPosterAdapter()
            rvRomantic.adapter = adapter
            rvRomantic.layoutManager =
                LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            adapter.submitList(model.list)

        }
    }

    object DisplayableDiffCallback : DiffUtil.ItemCallback<DisplayableItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val TITLE_TYPE = 1
        const val ADVERTISING_TYPE = 2
        const val POSTER_TYPE = 3
    }

}
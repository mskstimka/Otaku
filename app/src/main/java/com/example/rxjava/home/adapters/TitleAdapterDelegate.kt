package com.example.rxjava.home.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.home.DisplayableItem
import com.example.a16_rxjava_domain.models.home.Title
import com.example.rxjava.databinding.TitleItemsBinding

class TitleAdapterDelegate : AdapterDelegate<DisplayableItem> {

    private lateinit var binding: TitleItemsBinding


    override fun onCreateViewHolder(parent: ViewGroup): TitleViewHolder {
        binding = TitleItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is Title
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as TitleViewHolder).bind(items[position] as Title)
    }


    inner class TitleViewHolder(binding: TitleItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(titleItem: Title) {
            binding.tvGenreTitle.text = titleItem.title
        }
    }
}


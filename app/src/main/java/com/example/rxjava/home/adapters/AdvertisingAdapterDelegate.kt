package com.example.rxjava.home.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.home.Advertising
import com.example.a16_rxjava_domain.models.home.DisplayableItem
import com.example.rxjava.databinding.AdvertisingItemsBinding

class AdvertisingAdapterDelegate : AdapterDelegate<DisplayableItem> {

    private lateinit var binding: AdvertisingItemsBinding


    override fun onCreateViewHolder(parent: ViewGroup): AdvertisingViewHolder {
        binding =
            AdvertisingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdvertisingViewHolder(binding)
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is Advertising
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as AdvertisingViewHolder).bind(items[position] as Advertising)
    }


    inner class AdvertisingViewHolder(binding: AdvertisingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(advertisingItem: Advertising) {
        }
    }
}
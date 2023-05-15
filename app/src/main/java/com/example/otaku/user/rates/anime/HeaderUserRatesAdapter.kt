package com.example.otaku.user.rates.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.ItemAnimeUserRatesHeaderBinding

class HeaderUserRatesAdapter(
    private val onBackPressed: () -> Unit,
) : ListAdapter<HeaderUserRates, HeaderUserRatesAdapter.HeaderUserRatesViewHolder>(
        HeaderUserRatesInfoDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderUserRatesViewHolder {
        val binding =
            ItemAnimeUserRatesHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return HeaderUserRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderUserRatesViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class HeaderUserRatesViewHolder(
        private val binding:
        ItemAnimeUserRatesHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HeaderUserRates) = with(binding) {
            tvTitle.text = model.headerText

            ivBackPressed.setOnClickListener {
                onBackPressed()
            }

        }
    }


    object HeaderUserRatesInfoDiffCallback : DiffUtil.ItemCallback<HeaderUserRates>() {
        override fun areItemsTheSame(
            oldItem: HeaderUserRates,
            newItem: HeaderUserRates
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HeaderUserRates,
            newItem: HeaderUserRates
        ): Boolean {
            return oldItem == newItem
        }
    }
}

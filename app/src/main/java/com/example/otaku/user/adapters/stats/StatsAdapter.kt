package com.example.otaku.user.adapters.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.user.RateStatus
import com.example.otaku.R
import com.example.otaku.databinding.ItemUserStatsBinding
import com.example.otaku.user.adapters.stats.models.Stats
import com.example.otaku.user.rates.anime.AnimeRatesFragmentDirections
import com.example.otaku.user.ui.UserFragmentDirections

class StatsAdapter(private val userId: Long) :
    ListAdapter<Stats, StatsAdapter.StatsViewHolder>(
        StatsDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsViewHolder {
        val binding =
            ItemUserStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return StatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class StatsViewHolder(
        private val binding:
        ItemUserStatsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stats: Stats) = with(binding) {

            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToAnimeRatesFragment(
                        id = userId,
                        rateStatus = stats.type.status
                    )
                )
            }
            with(root.context) {

                tvStatus.text = when (stats.type) {
                    RateStatus.PLANNED -> {
                        getString(R.string.status_planned_text, stats.count.toString())
                    }
                    RateStatus.COMPLETED -> {
                        getString(R.string.status_completed_text, stats.count.toString())
                    }
                    RateStatus.WATCHING -> {
                        getString(R.string.status_watching_text, stats.count.toString())
                    }

                    RateStatus.ON_HOLD -> {
                        getString(R.string.status_on_hold_text, stats.count.toString())
                    }
                    RateStatus.DROPPED -> {
                        getString(R.string.status_dropped_text, stats.count.toString())
                    }
                    RateStatus.REWATCHING -> {
                        getString(R.string.status_rewatching_text, stats.count.toString())
                    }

                }
            }

        }
    }


    object StatsDiffCallback : DiffUtil.ItemCallback<Stats>() {
        override fun areItemsTheSame(
            oldItem: Stats,
            newItem: Stats
        ): Boolean {
            return oldItem.count == newItem.count
        }

        override fun areContentsTheSame(
            oldItem: Stats,
            newItem: Stats
        ): Boolean {
            return oldItem == newItem
        }
    }
}
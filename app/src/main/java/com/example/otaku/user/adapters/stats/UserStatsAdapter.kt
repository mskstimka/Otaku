package com.example.otaku.user.adapters.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.user.RateStatus
import com.example.domain.models.user.Status
import com.example.domain.models.user.UserDetails
import com.example.otaku.R
import com.example.otaku.databinding.LayoutUserStatsBinding
import com.example.otaku.user.adapters.stats.models.UserStatsContainer

class UserStatsAdapter(userId: Long) :
    ListAdapter<UserStatsContainer, UserStatsAdapter.UserStatsViewHolder>(
        UserStatsInfoDiffCallback
    ) {

    private val statsAdapter by lazy { StatsAdapter(userId = userId) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserStatsViewHolder {
        val binding =
            LayoutUserStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvStatuses.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvStatuses.adapter = statsAdapter
        return UserStatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserStatsViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<UserStatsContainer>?) {
        statsAdapter.submitList(list?.first()?.statsList ?: emptyList())
        super.submitList(list)
    }

    inner class UserStatsViewHolder(
        private val binding:
        LayoutUserStatsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserStatsContainer) = with(binding) {
            with(model) {
                val completed = statsList.find { it.type == RateStatus.COMPLETED }?.count ?: 0
                tvCompletedCount.text = completed.toString()
                tvOtherCount.text = others.toString()
                pbAnime.max = completed + others
                pbAnime.progress = others
                pbAnime.secondaryProgress = completed
            }
        }
    }

    object UserStatsInfoDiffCallback : DiffUtil.ItemCallback<UserStatsContainer>() {
        override fun areItemsTheSame(
            oldItem: UserStatsContainer,
            newItem: UserStatsContainer
        ): Boolean {
            return oldItem.details.id == newItem.details.id
        }

        override fun areContentsTheSame(
            oldItem: UserStatsContainer,
            newItem: UserStatsContainer
        ): Boolean {
            return oldItem == newItem
        }
    }

}
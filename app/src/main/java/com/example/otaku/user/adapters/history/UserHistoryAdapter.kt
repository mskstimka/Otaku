package com.example.otaku.user.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutUserHistoryBinding

class UserHistoryAdapter :
    ListAdapter<UserHistoryContainer, UserHistoryAdapter.UserHistoryViewHolder>(
        UserHistoryDiffCallback
    ) {

    private val historyAdapter by lazy { HistoryAdapter() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserHistoryViewHolder {
        val binding =
            LayoutUserHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvHistory.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvHistory.adapter = historyAdapter
        return UserHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHistoryViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<UserHistoryContainer>?) {
        historyAdapter.submitList(list?.first()?.list ?: emptyList())
        super.submitList(list)
    }

    inner class UserHistoryViewHolder(
        private val binding:
        LayoutUserHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserHistoryContainer) = with(binding) {

        }
    }

    object UserHistoryDiffCallback : DiffUtil.ItemCallback<UserHistoryContainer>() {
        override fun areItemsTheSame(
            oldItem: UserHistoryContainer,
            newItem: UserHistoryContainer
        ): Boolean {
            return oldItem.list.size == newItem.list.size
        }

        override fun areContentsTheSame(
            oldItem: UserHistoryContainer,
            newItem: UserHistoryContainer
        ): Boolean {
            return oldItem == newItem
        }
    }
}


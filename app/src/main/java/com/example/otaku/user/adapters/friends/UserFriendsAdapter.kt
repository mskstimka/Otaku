package com.example.otaku.user.adapters.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutUserFriendsBinding

class UserFriendsAdapter :
    ListAdapter<UserFriendsContainer, UserFriendsAdapter.UserFriendsViewHolder>(
        UserFriendsDiffCallback
    ) {

    private val friendsAdapter by lazy { FriendsAdapter() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFriendsViewHolder {
        val binding =
            LayoutUserFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvFriends.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvFriends.adapter = friendsAdapter
        return UserFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFriendsViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<UserFriendsContainer>?) {
        friendsAdapter.submitList(list?.first()?.list ?: emptyList())
        super.submitList(list)
    }

    inner class UserFriendsViewHolder(
        private val binding:
        LayoutUserFriendsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserFriendsContainer) = with(binding) {

        }
    }

    object UserFriendsDiffCallback : DiffUtil.ItemCallback<UserFriendsContainer>() {
        override fun areItemsTheSame(
            oldItem: UserFriendsContainer,
            newItem: UserFriendsContainer
        ): Boolean {
            return oldItem.list.size == newItem.list.size
        }

        override fun areContentsTheSame(
            oldItem: UserFriendsContainer,
            newItem: UserFriendsContainer
        ): Boolean {
            return oldItem == newItem
        }
    }

}
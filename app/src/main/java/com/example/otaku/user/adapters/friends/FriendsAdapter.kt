package com.example.otaku.user.adapters.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.user.UserBrief
import com.example.otaku.databinding.ItemFavoritesPostersBinding
import com.example.otaku.databinding.ItemFriendsBinding
import com.example.otaku.user.ui.UserFragmentDirections
import com.example.otaku.utils.setImageByURL

class FriendsAdapter :
    ListAdapter<UserBrief, FriendsAdapter.FriendsViewHolder>(
        FriendsDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        val binding =
            ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class FriendsViewHolder(
        private val binding:
        ItemFriendsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserBrief) = with(binding) {

            ivAvatar.setImageByURL(model.image.x160 ?: "")
            tvNickName.text = model.nickname

            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentSelf(
                        id = model.id
                    )
                )
            }



        }
    }


    object FriendsDiffCallback : DiffUtil.ItemCallback<UserBrief>() {
        override fun areItemsTheSame(
            oldItem: UserBrief,
            newItem: UserBrief
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserBrief,
            newItem: UserBrief
        ): Boolean {
            return oldItem == newItem
        }
    }
}
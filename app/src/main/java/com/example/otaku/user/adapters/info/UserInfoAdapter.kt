package com.example.otaku.user.adapters.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.user.UserBrief
import com.example.otaku.databinding.LayoutUserInfoBinding
import com.example.otaku.utils.setImageByURL

class UserInfoAdapter :
    ListAdapter<UserBrief, UserInfoAdapter.UserInfoViewHolder>(
        UserInfoDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserInfoViewHolder {
        val binding =
            LayoutUserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class UserInfoViewHolder(
        private val binding:
        LayoutUserInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserBrief) = with(binding) {

            ivImageBackground.setImageByURL(model.image.x160 ?: "null")
            ivUserAvatar.setImageByURL(model.image.x160 ?: "null")
            tvTitle.text = model.nickname

        }
    }


    object UserInfoDiffCallback : DiffUtil.ItemCallback<UserBrief>() {
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
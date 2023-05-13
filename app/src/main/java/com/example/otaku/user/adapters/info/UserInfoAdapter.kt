package com.example.otaku.user.adapters.info

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku_domain.models.user.UserBrief
import com.example.otaku.databinding.LayoutUserInfoBinding
import com.example.otaku.utils.setImageByURL

class UserInfoAdapter(
    private val onBackPressed: () -> Unit,
    private val actionFriends: (actionFriends: ActionFriends) -> Unit
) :
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

            val addToFriendsText = root.context.getString(R.string.add_to_friends_text)
            val yourFriendsText = root.context.getString(R.string.your_friend_text)
            ivImageBackground.setImageByURL(model.image.x160 ?: "null")
            ivUserAvatar.setImageByURL(model.image.x160 ?: "null")
            tvTitle.text = model.nickname
            ivBackPressed.setOnClickListener {
                onBackPressed()
            }


            if (model.id != model.currentUserId) {

                tvInFriends.visibility = View.VISIBLE

                if (model.inFriends == true) {
                    tvInFriends.text = yourFriendsText
                    tvInFriends.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_user_added, 0, 0, 0
                    )
                } else {
                    tvInFriends.text = addToFriendsText
                    tvInFriends.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_user_no_added, 0, 0, 0
                    )
                }


                tvInFriends.setOnClickListener {
                    if (model.inFriends == true) {
                        actionFriends(ActionFriends.DELETE_FRIEND(model.id))
                        tvInFriends.text = addToFriendsText
                        tvInFriends.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.icon_user_no_added, 0, 0, 0
                        )
                        model.inFriends = null
                    } else {
                        actionFriends(ActionFriends.ADD_TO_FRIENDS(model.id))
                        tvInFriends.text = yourFriendsText
                        tvInFriends.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.icon_user_added, 0, 0, 0
                        )
                        model.inFriends = true
                    }
                }
            }

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
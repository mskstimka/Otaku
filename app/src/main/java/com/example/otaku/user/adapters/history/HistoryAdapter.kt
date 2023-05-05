package com.example.otaku.user.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.SHIKIMORI_URL
import com.example.domain.models.user.history.UserHistory
import com.example.otaku.databinding.ItemHistoryBinding
import com.example.otaku.utils.setImageByURL

class HistoryAdapter :
    ListAdapter<UserHistory, HistoryAdapter.HistoryViewHolder>(
        HistoryDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class HistoryViewHolder(
        private val binding:
        ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserHistory) = with(binding) {
            ivAvatar.setImageByURL("$SHIKIMORI_URL${model.target?.image?.x96}" ?: "")
            tvDate.text = model.dateCreated.toString("dd.MM.yyyy HH:mm:ss")
            tvName.text = model.target?.name
            tvDescription.text =
                HtmlCompat.fromHtml(model.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }


    object HistoryDiffCallback : DiffUtil.ItemCallback<UserHistory>() {
        override fun areItemsTheSame(
            oldItem: UserHistory,
            newItem: UserHistory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserHistory,
            newItem: UserHistory
        ): Boolean {
            return oldItem == newItem
        }
    }
}
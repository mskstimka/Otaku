package com.example.animator.details.info.adapters.authors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.LayoutAuthorsInfoBinding

class ContainerAuthorsAdapter :
    ListAdapter<ContainerAuthors, ContainerAuthorsAdapter.ParentAuthorsViewHolder>(
        ParentAuthorsDiffCallback
    ) {

    private val authorsAdapter by lazy { AuthorsAdapter() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAuthorsViewHolder {
        val binding =
            LayoutAuthorsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvAutors.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvAutors.adapter = authorsAdapter

        return ParentAuthorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentAuthorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentAuthorsViewHolder(
        private val binding:
        LayoutAuthorsInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ContainerAuthors) = with(binding) {

            authorsAdapter.submitList(item.list)
        }
    }


    object ParentAuthorsDiffCallback : DiffUtil.ItemCallback<ContainerAuthors>() {
        override fun areItemsTheSame(
            oldItem: ContainerAuthors,
            newItem: ContainerAuthors
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerAuthors,
            newItem: ContainerAuthors
        ): Boolean {
            return oldItem == newItem
        }
    }
}
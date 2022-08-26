package com.example.animator.details.adapters.roles.authors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.LayoutAuthorsInfoBinding
import com.example.animator.details.adapters.roles.ContainerRoles

class ContainerAuthorsAdapter :
    ListAdapter<ContainerRoles, ContainerAuthorsAdapter.ParentAuthorsViewHolder>(
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
        fun bind(item: ContainerRoles) = with(binding) {

            authorsAdapter.submitList(item.list)
        }
    }


    object ParentAuthorsDiffCallback : DiffUtil.ItemCallback<ContainerRoles>() {
        override fun areItemsTheSame(
            oldItem: ContainerRoles,
            newItem: ContainerRoles
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerRoles,
            newItem: ContainerRoles
        ): Boolean {
            return oldItem == newItem
        }
    }
}
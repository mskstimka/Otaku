package com.example.animator.details.adapters.roles.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.LayoutCharactersInfoBinding
import com.example.animator.details.adapters.roles.ContainerRoles

class ContainerCharactersAdapter :
    ListAdapter<ContainerRoles, ContainerCharactersAdapter.ParentCharactersViewHolder>(
        ParentCharactersDiffCallback
    ) {

    private val charactersAdapter by lazy { CharactersAdapter() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentCharactersViewHolder {
        val binding =
            LayoutCharactersInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvCharacters.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvCharacters.adapter = charactersAdapter

        return ParentCharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentCharactersViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentCharactersViewHolder(
        private val binding:
        LayoutCharactersInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ContainerRoles) = with(binding) {

            charactersAdapter.submitList(item.list)
        }
    }


    object ParentCharactersDiffCallback : DiffUtil.ItemCallback<ContainerRoles>() {
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
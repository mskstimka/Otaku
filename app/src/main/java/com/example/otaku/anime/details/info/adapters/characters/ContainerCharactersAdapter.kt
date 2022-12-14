package com.example.otaku.anime.details.info.adapters.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutDetailsCharactersInfoBinding

class ContainerCharactersAdapter(private val actionToCharacters: (id: Int) -> Unit) :
    ListAdapter<ContainerCharacters, ContainerCharactersAdapter.ParentCharactersViewHolder>(
        ParentCharactersDiffCallback
    ) {

    private val charactersAdapter by lazy { CharactersAdapter { actionToCharacters(it) } }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentCharactersViewHolder {
        val binding =
            LayoutDetailsCharactersInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

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
        LayoutDetailsCharactersInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerCharacters) = with(binding) {

            charactersAdapter.submitList(item.list)
        }
    }


    object ParentCharactersDiffCallback : DiffUtil.ItemCallback<ContainerCharacters>() {
        override fun areItemsTheSame(
            oldItem: ContainerCharacters,
            newItem: ContainerCharacters
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerCharacters,
            newItem: ContainerCharacters
        ): Boolean {
            return oldItem == newItem
        }
    }
}
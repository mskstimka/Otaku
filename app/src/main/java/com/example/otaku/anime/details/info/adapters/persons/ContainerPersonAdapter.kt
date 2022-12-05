package com.example.otaku.anime.details.info.adapters.persons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutDetailsAuthorsInfoBinding

class ContainerPersonAdapter(private val actionToPerson: (id: Int) -> Unit) :
    ListAdapter<ContainerPerson, ContainerPersonAdapter.ParentAuthorsViewHolder>(
        ParentAuthorsDiffCallback
    ) {

    private val personAdapter by lazy { PersonAdapter { actionToPerson(it) } }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAuthorsViewHolder {
        val binding =
            LayoutDetailsAuthorsInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        binding.rvAutors.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvAutors.adapter = personAdapter

        return ParentAuthorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentAuthorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentAuthorsViewHolder(
        private val binding:
        LayoutDetailsAuthorsInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerPerson) = with(binding) {

            personAdapter.submitList(item.list)

            binding.tvTitleAutors.text = item.title
        }
    }


    object ParentAuthorsDiffCallback : DiffUtil.ItemCallback<ContainerPerson>() {
        override fun areItemsTheSame(
            oldItem: ContainerPerson,
            newItem: ContainerPerson
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerPerson,
            newItem: ContainerPerson
        ): Boolean {
            return oldItem == newItem
        }
    }
}
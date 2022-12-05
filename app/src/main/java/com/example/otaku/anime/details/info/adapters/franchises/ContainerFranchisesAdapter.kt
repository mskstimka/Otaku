package com.example.otaku.anime.details.info.adapters.franchises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutDetailsFranchisesInfoBinding

class ContainerFranchisesAdapter(private val actionToFranchises: (id: Int) -> Unit) :
    ListAdapter<ContainerFranchises, ContainerFranchisesAdapter.ParentFranchisesViewHolder>(
        ParentFranchisesDiffCallback
    ) {

    private val franchisesAdapter by lazy { FranchisesAdapter { actionToFranchises(it) } }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentFranchisesViewHolder {
        val binding =
            LayoutDetailsFranchisesInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        binding.rvFranchises.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvFranchises.adapter = franchisesAdapter

        return ParentFranchisesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentFranchisesViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentFranchisesViewHolder(
        private val binding:
        LayoutDetailsFranchisesInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContainerFranchises) = with(binding) {

            franchisesAdapter.submitList(item.list)
        }
    }


    object ParentFranchisesDiffCallback : DiffUtil.ItemCallback<ContainerFranchises>() {
        override fun areItemsTheSame(
            oldItem: ContainerFranchises,
            newItem: ContainerFranchises
        ): Boolean {
            return oldItem.id.equals(oldItem.id, true)
        }

        override fun areContentsTheSame(
            oldItem: ContainerFranchises,
            newItem: ContainerFranchises
        ): Boolean {
            return oldItem == newItem
        }
    }
}
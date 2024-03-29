package com.example.otaku.persons.adapters.roles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutPersonRoleBinding

class ContainerWorksAdapter(private val actionToDetails: (id: Int) -> Unit) :
    ListAdapter<ContainerWorks, ContainerWorksAdapter.ContainerWorksViewHolder>(
        ContainerWorksDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerWorksViewHolder {
        val binding =
            LayoutPersonRoleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        val worksAdapter by lazy { WorksAdapter { actionToDetails(it) } }

        binding.rvRoot.adapter = worksAdapter
        binding.rvRoot.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)

        return ContainerWorksViewHolder(binding, worksAdapter)
    }

    override fun onBindViewHolder(holder: ContainerWorksViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerWorksViewHolder(
        private val binding:
        LayoutPersonRoleBinding, private val adapter: WorksAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerWorks) = with(binding) {
            adapter.submitList(model.list)
        }
    }


    object ContainerWorksDiffCallback : DiffUtil.ItemCallback<ContainerWorks>() {
        override fun areItemsTheSame(
            oldItem: ContainerWorks,
            newItem: ContainerWorks
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerWorks,
            newItem: ContainerWorks
        ): Boolean {
            return oldItem == newItem
        }
    }
}
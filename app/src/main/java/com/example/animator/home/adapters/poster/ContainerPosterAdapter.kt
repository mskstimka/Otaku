package com.example.animator.home.adapters.poster


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator.databinding.ItemHomePosterBinding

class ContainerPosterAdapter :
    ListAdapter<ContainerPoster, ContainerPosterAdapter.ContainerPosterViewHolder>(
        ContainerPosterDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerPosterViewHolder {
        val binding =
            ItemHomePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContainerPosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerPosterViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerPosterViewHolder(
        private val binding:
        ItemHomePosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerPoster) = with(binding) {


            itemView.setOnClickListener {
                model.action()
            }

        }
    }


    object ContainerPosterDiffCallback : DiffUtil.ItemCallback<ContainerPoster>() {
        override fun areItemsTheSame(
            oldItem: ContainerPoster,
            newItem: ContainerPoster
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerPoster,
            newItem: ContainerPoster
        ): Boolean {
            return oldItem == newItem
        }
    }
}
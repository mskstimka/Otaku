package com.example.otaku.home.adapters.poster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.otaku.databinding.LayoutHomePosterBinding

class ContainerPosterAdapter :
    ListAdapter<ContainerPoster, ContainerPosterAdapter.ContainerPosterViewHolder>(
        ContainerPosterDiffCallback
    ) {

    private val newsPosterAdapter by lazy { NewsPosterAdapter() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerPosterViewHolder {
        val binding =
            LayoutHomePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.apply {
            layoutManager =
                LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
            adapter = newsPosterAdapter
            addItemDecoration(LinePagerIndicatorDecoration())
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)

        }
        return ContainerPosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerPosterViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerPosterViewHolder(
        private val binding:
        LayoutHomePosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerPoster) = with(binding) {
            newsPosterAdapter.submitList(model.list)
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
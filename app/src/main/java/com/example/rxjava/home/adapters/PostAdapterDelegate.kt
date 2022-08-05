package com.example.rxjava.home.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.home.DisplayableItem
import com.example.a16_rxjava_domain.models.home.PrevPoster
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.databinding.ItemFranchisesBinding
import com.example.rxjava.databinding.PostItemsBinding
import com.example.rxjava.details.adapters.FranchisesAdapter

class PostAdapterDelegate : AdapterDelegate<DisplayableItem> {

    private lateinit var binding: PostItemsBinding

    private val adapter: PrevPosterAdapter by lazy { PrevPosterAdapter() }

    override fun onCreateViewHolder(parent: ViewGroup): PostViewHolder {
        binding = PostItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is PrevPoster
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as PostViewHolder).bind(items[position] as PrevPoster)

    }


    inner class PostViewHolder(binding: PostItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(prevPosterItem: PrevPoster) {
            adapter.submitList(prevPosterItem.list)
            binding.rvRomantic.adapter = adapter
            binding.rvRomantic.layoutManager =
                LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)

        }
    }
}


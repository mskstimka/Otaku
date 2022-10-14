package com.example.otaku.home.adapters.random

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.ItemHomeRandomBinding
import com.example.otaku.details.info.adapters.screenshots.ScreenshotsAdapter
import com.example.otaku.home.ui.HomeFragmentDirections
import com.example.otaku.utils.setImageByURL
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity

class ContainerRandomAdapter(private val action: () -> Unit) :
    ListAdapter<ContainerRandom, ContainerRandomAdapter.ContainerRandomViewHolder>(
        ContainerRandomDiffCallback
    ) {

    private val screenshotsAdapter by lazy { ScreenshotsAdapter() }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerRandomViewHolder {
        val binding =
            ItemHomeRandomBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.rvItemHomeRandomScreenshots.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvItemHomeRandomScreenshots.adapter = screenshotsAdapter

        return ContainerRandomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerRandomViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerRandomViewHolder(
        private val binding:
        ItemHomeRandomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerRandom) = with(binding) {

            ivItemHomeRandomRefresh.setOnClickListener {

                action()
                val rotateAnimation= RotateAnimation(0f, 359f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
                rotateAnimation.duration = 300
                it.startAnimation(rotateAnimation)


            }

            viewContainer.setOnClickListener {
                itemView.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(model.item.id)
                )
            }

            tvItemHomeRandomName.text = model.item.name
            tvItemHomeRandomRussian.text = model.item.russian
            tvItemHomeRandomEpisodes.text = if (model.item.episodes.toString() != "0") {
                root.context.getString(R.string.episode_text, model.item.episodes.toString())
            } else {
                root.context.getString(R.string.episode_text, model.item.episodesAired.toString())
            }
            ivItemHomeRandomImage.setImageByURL(
                SHIKIMORI_URL + model.item.image.original
            )
            tvItemHomeRandomName.text = model.item.name

        }
    }

    fun setItemsScreenshots(list: List<AnimeDetailsScreenshotsEntity>) {
        screenshotsAdapter.submitList(list)
    }

    object ContainerRandomDiffCallback : DiffUtil.ItemCallback<ContainerRandom>() {
        override fun areItemsTheSame(
            oldItem: ContainerRandom,
            newItem: ContainerRandom
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerRandom,
            newItem: ContainerRandom
        ): Boolean {
            return oldItem == newItem
        }
    }
}
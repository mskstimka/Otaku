package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemPostersBinding
import com.example.a16_rxjava_domain.models.Constants
import com.example.rxjava.ui.fragments.SearchFragmentDirections
import com.squareup.picasso.Picasso

class PostersAdapter :
    ListAdapter<AnimePosterEntity, PostersAdapter.TitleViewHolder>(CurrencyDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding =
            ItemPostersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class TitleViewHolder(
        private val binding: ItemPostersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(model: AnimePosterEntity) = with(binding) {
            tvName.text = model.name
            tvScore.text = model.score

            if (model.episodes.toString() != "0") {
                tvEpisodes.text = "Episodes: ${model.episodes}"
            } else {
                tvEpisodes.text = "Episodes: ${model.episodesAired}"
            }
            tvStatus.text = model.status
            when (tvStatus.text) {
                Constants.ONGOING_STATUS -> tvStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.blue_status
                    )
                )
                Constants.ANONS_STATUS -> tvStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.red_status
                    )
                )
                Constants.RELEASED_STATUS -> tvStatus.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.green_status
                    )
                )
            }
            Picasso.get().load(Constants.SHIKIMORI_URL + model.image.original)
                .error(R.drawable.icon_default).into(ivImageFranchisesItem)

            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(model.id)
                )
            }
        }
    }

    object CurrencyDiffCallback : DiffUtil.ItemCallback<AnimePosterEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimePosterEntity,
            newItem: AnimePosterEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimePosterEntity,
            newItem: AnimePosterEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

}
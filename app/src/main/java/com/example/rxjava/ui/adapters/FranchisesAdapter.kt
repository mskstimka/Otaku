package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemFranchisesBinding
import com.example.rxjava.ui.fragments.DetailsFragmentDirections
import com.squareup.picasso.Picasso

class FranchisesAdapter(context: Context) :
    ListAdapter<AnimeDetailsFranchisesEntity, FranchisesAdapter.FranchisesViewHolder>(
        FranchisesDiffCallback
    ) {
    private val message = context.getString(R.string.not_found)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FranchisesViewHolder {
        val binding =
            ItemFranchisesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FranchisesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FranchisesViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class FranchisesViewHolder(
        private val binding:
        ItemFranchisesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsFranchisesEntity) = with(binding) {

            Picasso.get().load(model.image_url.replace("x96", "original"))
                .error(R.drawable.icon_default).into(ivImageFranchisesItem)
            tvTitleFranchisesItem.text = model.name
            tvKindDateFranchisesItem.text = "${model.kind} / ${model.year}"

            itemView.setOnClickListener {
                if (model.kind != message)
                    itemView.findNavController().navigate(
                        DetailsFragmentDirections.actionDetailsFragmentSelf(model.id)
                    )
            }
        }

    }

    override fun submitList(list: List<AnimeDetailsFranchisesEntity>?) {


        val franchisesList = when (list) {
            emptyList<AnimeDetailsFranchisesEntity>() -> listOf(
                AnimeDetailsFranchisesEntity(
                    404, 0, "x96", message, message, message, 404, 404
                )
            )
            else -> list
        }
        super.submitList(franchisesList)
    }

    object FranchisesDiffCallback : DiffUtil.ItemCallback<AnimeDetailsFranchisesEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsFranchisesEntity,
            newItem: AnimeDetailsFranchisesEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsFranchisesEntity,
            newItem: AnimeDetailsFranchisesEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}

package com.example.rxjava.details.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.Image
import com.example.a16_rxjava_domain.models.details.roles.Person
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemAutorsBinding
import com.example.a16_rxjava_domain.Constants
import com.squareup.picasso.Picasso

class AutorsAdapter(context: Context) :
    ListAdapter<AnimeDetailsRolesEntity, AutorsAdapter.AutorsViewHolder>(
        CharactersDiffCallback
    ) {

    private val message = context.getString(R.string.not_found)
    private val defaultPerson =
        Person(404, Image(message, message, message, message), message, message, message)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutorsViewHolder {
        val binding =
            ItemAutorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AutorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AutorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class AutorsViewHolder(
        private val binding:
        ItemAutorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsRolesEntity) = with(binding) {

            Picasso.get().load(Constants.SHIKIMORI_URL + model.person?.image?.original)
                .error(R.drawable.icon_default).into(ivImageAutorsItem)
            tvTitleAutorsItem.text = model.person?.name
            tvRoleAutorsItem.text = model.roles_russian.toString()
        }
    }

    override fun submitList(list: List<AnimeDetailsRolesEntity>?) {
        val autorsList = when (list) {
            emptyList<AnimeDetailsRolesEntity>() -> listOf(
                AnimeDetailsRolesEntity(
                    null, defaultPerson, null, null, message
                )
            )
            else -> list?.filter { it.person != null }
        }

        super.submitList(autorsList)
    }

    object CharactersDiffCallback : DiffUtil.ItemCallback<AnimeDetailsRolesEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsRolesEntity,
            newItem: AnimeDetailsRolesEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsRolesEntity,
            newItem: AnimeDetailsRolesEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}



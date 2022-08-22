package com.example.rxjava.details.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.NOT_FOUND_TEXT
import com.example.a16_rxjava_domain.SHIKIMORI_URL
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.Image
import com.example.a16_rxjava_domain.models.details.roles.Person
import com.example.rxjava.databinding.ItemDetailsAutorsBinding
import com.example.rxjava.utils.setImageByURL


class AutorsAdapter :
    ListAdapter<AnimeDetailsRolesEntity, AutorsAdapter.AutorsViewHolder>(
        CharactersDiffCallback
    ) {

    private val defaultPerson =
        Person(
            id = 404,
            image = Image(
                original = NOT_FOUND_TEXT,
                preview = NOT_FOUND_TEXT,
                x48 = NOT_FOUND_TEXT,
                x96 = NOT_FOUND_TEXT
            ),
            name = NOT_FOUND_TEXT, russian = NOT_FOUND_TEXT, url = NOT_FOUND_TEXT
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutorsViewHolder {
        val binding =
            ItemDetailsAutorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AutorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AutorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class AutorsViewHolder(
        private val binding:
        ItemDetailsAutorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsRolesEntity) = with(binding) {

            ivImageAutorsItem.setImageByURL(SHIKIMORI_URL + model.person?.image?.original)
            tvTitleAutorsItem.text = model.person?.name
            tvRoleAutorsItem.text = model.roles_russian.toString()
        }
    }

    override fun submitList(list: List<AnimeDetailsRolesEntity>?) {
        val autorsList = when (list) {
            emptyList<AnimeDetailsRolesEntity>() -> listOf(
                AnimeDetailsRolesEntity(
                    character = null,
                    person = defaultPerson,
                    roles = null,
                    roles_russian = null,
                    id = NOT_FOUND_TEXT
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



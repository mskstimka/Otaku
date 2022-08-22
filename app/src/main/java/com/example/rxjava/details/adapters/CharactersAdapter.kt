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
import com.example.a16_rxjava_domain.models.details.roles.Character
import com.example.a16_rxjava_domain.models.Image
import com.example.rxjava.databinding.ItemDetailsCharactersBinding
import com.example.rxjava.utils.setImageByURL

class CharactersAdapter :
    ListAdapter<AnimeDetailsRolesEntity, CharactersAdapter.CharactersViewHolder>(
        CharactersDiffCallback
    ) {


    private val defaultCharacter =
        Character(
            id = 404,
            image = Image(
                original = NOT_FOUND_TEXT,
                preview = NOT_FOUND_TEXT,
                x48 = NOT_FOUND_TEXT,
                x96 = NOT_FOUND_TEXT
            ),
            name = NOT_FOUND_TEXT, russian = NOT_FOUND_TEXT, url = NOT_FOUND_TEXT
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            ItemDetailsCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class CharactersViewHolder(
        private val binding:
        ItemDetailsCharactersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: AnimeDetailsRolesEntity) = with(binding) {

            ivImageCharactersItem.setImageByURL(SHIKIMORI_URL + model.character?.image?.original)
            tvTitleCharactersItem.text = model.character?.name
        }
    }

    override fun submitList(list: List<AnimeDetailsRolesEntity>?) {
        val charactersList = when (list) {
            emptyList<AnimeDetailsRolesEntity>() -> listOf(
                AnimeDetailsRolesEntity(
                    character = defaultCharacter,
                    person = null,
                    roles = null,
                    roles_russian = null,
                    id = NOT_FOUND_TEXT
                )
            )
            else -> list?.filter { it.character != null }
        }

        super.submitList(charactersList)
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
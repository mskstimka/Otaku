package com.example.rxjava.details.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.roles.Character
import com.example.a16_rxjava_domain.models.Image
import com.example.rxjava.R
import com.example.a16_rxjava_domain.Constants
import com.example.rxjava.databinding.ItemDetailsCharactersBinding
import com.squareup.picasso.Picasso

class CharactersAdapter(context: Context) :
    ListAdapter<AnimeDetailsRolesEntity, CharactersAdapter.CharactersViewHolder>(
        CharactersDiffCallback
    ) {

    private val message = context.getString(R.string.not_found)
    private val defaultCharacter =
        Character(404, Image(message, message, message, message), message, message, message)

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

            Picasso.get().load(Constants.SHIKIMORI_URL + model.character?.image?.original)
                .error(R.drawable.icon_default).into(ivImageCharactersItem)

            tvTitleCharactersItem.text = model.character?.name

        }
    }

    override fun submitList(list: List<AnimeDetailsRolesEntity>?) {
        val charactersList = when (list) {
            emptyList<AnimeDetailsRolesEntity>() -> listOf(
                AnimeDetailsRolesEntity(
                    defaultCharacter, null, null, null, message
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
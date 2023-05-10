package com.example.otaku.anime.details.info.adapters.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.details.roles.Character
import com.example.otaku.databinding.ItemDetailsCharactersBinding
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.NOT_FOUND_TEXT

class CharactersAdapter(private val actionToCharacters: (id: Int) -> Unit) :
    ListAdapter<Character, CharactersAdapter.CharactersViewHolder>(
        CharactersDiffCallback
    ) {

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
        fun bind(model: Character) = with(binding) {

            ivImageCharactersItem.setImageByURL(SHIKIMORI_URL + model.image.original)
            tvTitleCharactersItem.text = model.name

            itemView.setOnClickListener {
                if (model.name != NOT_FOUND_TEXT) {
                    actionToCharacters(model.id)
                }
            }
        }
    }

    object CharactersDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem == newItem
        }
    }
}
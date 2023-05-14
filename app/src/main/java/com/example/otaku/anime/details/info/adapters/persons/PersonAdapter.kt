package com.example.otaku.anime.details.info.adapters.persons

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.ItemDetailsAutorsBinding
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.NOT_FOUND_TEXT
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.details.roles.Person


class PersonAdapter(private val actionToPerson: (id: Int) -> Unit) :
    ListAdapter<Person, PersonAdapter.AuthorsViewHolder>(
        CharactersDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val binding =
            ItemDetailsAutorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class AuthorsViewHolder(
        private val binding:
        ItemDetailsAutorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Person) = with(binding) {

            ivImageAutorsItem.setImageByURL(SHIKIMORI_URL + model.image.original)
            tvTitleAutorsItem.text = model.name
            tvRoleAutorsItem.text = model.russian

            itemView.setOnClickListener {
                if (model.name != NOT_FOUND_TEXT) {
                    actionToPerson(model.id)
                }
            }
        }
    }

    object CharactersDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(
            oldItem: Person,
            newItem: Person
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Person,
            newItem: Person
        ): Boolean {
            return oldItem == newItem
        }
    }
}



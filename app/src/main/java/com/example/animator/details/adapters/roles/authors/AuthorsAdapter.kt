package com.example.animator.details.adapters.roles.authors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.NOT_FOUND_TEXT
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.Image
import com.example.animator_domain.models.details.roles.Person
import com.example.animator.databinding.ItemDetailsAutorsBinding
import com.example.animator.utils.setImageByURL


class AuthorsAdapter :
    ListAdapter<AnimeDetailsRolesEntity, AuthorsAdapter.AuthorsViewHolder>(
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
        fun bind(model: AnimeDetailsRolesEntity) = with(binding) {

            ivImageAutorsItem.setImageByURL(SHIKIMORI_URL + model.person?.image?.original)
            tvTitleAutorsItem.text = model.person?.name
            tvRoleAutorsItem.text = model.roles_russian.toString()
        }
    }

    override fun submitList(list: List<AnimeDetailsRolesEntity>?) {
        val authorsList = when (list) {
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

        super.submitList(authorsList)
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



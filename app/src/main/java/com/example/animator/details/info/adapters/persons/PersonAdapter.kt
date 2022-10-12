package com.example.animator.details.info.adapters.persons

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.SHIKIMORI_URL
import com.example.animator_domain.models.details.roles.Person
import com.example.animator.databinding.ItemDetailsAutorsBinding
import com.example.animator.details.info.ui.DetailsFragmentDirections
import com.example.animator.utils.setImageByURL
import com.example.animator_domain.NOT_FOUND_TEXT


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



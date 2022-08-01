package com.example.rxjava.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.details.Genre
import com.example.rxjava.R
import com.example.rxjava.databinding.ItemGenresBinding

class GenresAdapter(context: Context) :
    ListAdapter<Genre, GenresAdapter.GenreViewHolder>(GenreDiffCallback) {

    private val message = context.getString(R.string.not_found)
    private val defaultGenre = Genre(404, message, message, message)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class GenreViewHolder(
        private val binding: ItemGenresBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: Genre) = with(binding) {

            tvTitleGenreItem.text = model.russian


        }
    }


    override fun submitList(list: List<Genre>?) {
        val genresList = when (list) {
            emptyList<Genre>() -> listOf(defaultGenre)
            else -> list
        }

        super.submitList(genresList)
    }

    object GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem == newItem
        }
    }
}
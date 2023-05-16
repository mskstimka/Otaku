package com.example.otaku.home.adapters.poster


import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.ItemHomePosterBinding

class NewsPosterAdapter :
    ListAdapter<NewsPoster, NewsPosterAdapter.ContainerPosterViewHolder>(
        ContainerPosterDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerPosterViewHolder {
        val binding =
            ItemHomePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContainerPosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerPosterViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerPosterViewHolder(
        private val binding:
        ItemHomePosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NewsPoster) = with(binding) {
            btToSettings.setOnClickListener {
                model.action()
            }

            textView.text = root.context.getText(model.textId)
            btToSettings.text = root.context.getText(model.buttonTextId)
            ivItemHomePosterImage.setImageResource(model.imageId)

            val color = model.buttonColor
            if (color != null) {
                val color = root.context.getColor(color)

                btToSettings.backgroundTintList = ColorStateList.valueOf(color)
            }

            if (model.id == "UPDATE"){
                val layoutParams = btToSettings.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(10, 0, 25,0)
            }
        }
    }


    object ContainerPosterDiffCallback : DiffUtil.ItemCallback<NewsPoster>() {
        override fun areItemsTheSame(
            oldItem: NewsPoster,
            newItem: NewsPoster
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsPoster,
            newItem: NewsPoster
        ): Boolean {
            return oldItem == newItem
        }
    }
}
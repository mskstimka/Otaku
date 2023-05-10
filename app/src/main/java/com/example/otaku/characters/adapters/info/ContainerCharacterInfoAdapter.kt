package com.example.otaku.characters.adapters.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutCharactersInfoBinding
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.NOT_FOUND_TEXT
import com.example.otaku_domain.SHIKIMORI_URL

class ContainerCharacterInfoAdapter(private val onBackPressed: () -> Unit) :
    ListAdapter<ContainerCharacterInfo, ContainerCharacterInfoAdapter.ParentAuthorsViewHolder>(
        ContainerCharacterInfoDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAuthorsViewHolder {
        val binding =
            LayoutCharactersInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ParentAuthorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentAuthorsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentAuthorsViewHolder(
        private val binding:
        LayoutCharactersInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerCharacterInfo) = with(binding) {

            with(model.item) {
                tvTitle.text = name
                tvTitleRussian.text = nameRu

                ivImageFranchises.setImageByURL(SHIKIMORI_URL + model.item.image.original)
                ivImageBackground.setImageByURL(SHIKIMORI_URL + model.item.image.original)
                exDescription.text = if (description != null) {
                    HtmlCompat.fromHtml(description_html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                } else {
                    NOT_FOUND_TEXT
                }

                tvJapan.text = nameJp

                ivBackPressed.setOnClickListener {
                    onBackPressed()
                }
            }
        }
    }


    object ContainerCharacterInfoDiffCallback : DiffUtil.ItemCallback<ContainerCharacterInfo>() {
        override fun areItemsTheSame(
            oldItem: ContainerCharacterInfo,
            newItem: ContainerCharacterInfo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerCharacterInfo,
            newItem: ContainerCharacterInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}

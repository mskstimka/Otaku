package com.example.otaku.characters.adapters.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.LayoutCharactersInfoBinding
import com.example.otaku.utils.FavoriteAction
import com.example.otaku.utils.animShake
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.NOT_FOUND_TEXT
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.user.Type

class ContainerCharacterInfoAdapter(
    private val onBackPressed: () -> Unit,
    private val favoriteAction: (favoriteAction: FavoriteAction) -> Unit
) :
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

                if (model.item.favorued == true) {
                    ivFavorite.setImageResource(R.drawable.icon_favorite_true)
                } else {
                    ivFavorite.setImageResource(R.drawable.icon_favorite_false)
                }

                ivFavorite.setOnClickListener {
                    if (model.item.favorued == true) {
                        favoriteAction(
                            FavoriteAction.DELETE_FAVORITE(
                                linkedId = model.item.id,
                                linkedType = Type.CHARACTER
                            )
                        )
                        it.animShake {

                            model.item.favorued = false
                            ivFavorite.setImageResource(R.drawable.icon_favorite_false)
                        }
                    } else if (model.item.favorued == null || model.item.favorued == false) {
                        favoriteAction(
                            FavoriteAction.CREATE_FAVORITE(
                                linkedId = model.item.id,
                                linkedType = Type.CHARACTER
                            )
                        )
                        it.animShake {
                            model.item.favorued = true
                            ivFavorite.setImageResource(R.drawable.icon_favorite_true)
                        }
                    }
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

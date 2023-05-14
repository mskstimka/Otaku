package com.example.otaku.persons.adapters.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.R
import com.example.otaku.databinding.LayoutPersonInfoBinding
import com.example.otaku.utils.FavoriteAction
import com.example.otaku.utils.animShake
import com.example.otaku.utils.setImageByURL
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.user.Type

class ContainerPersonInfoAdapter(
    private val onBackPressed: () -> Unit,
    private val favoriteAction: (favoriteAction: FavoriteAction) -> Unit
) :
    ListAdapter<ContainerPersonInfo, ContainerPersonInfoAdapter.ContainerPersonInfoViewHolder>(
        ContainerPersonInfoDiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContainerPersonInfoViewHolder {
        val binding =
            LayoutPersonInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ContainerPersonInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerPersonInfoViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ContainerPersonInfoViewHolder(
        private val binding:
        LayoutPersonInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContainerPersonInfo) = with(binding) {

            with(model.item) {
                tvTitle.text = name
                tvTitleRussian.text = nameRu
                tvKind.text = jobTitle
                tvJapan.text = nameJp

                ivImageFranchises.setImageByURL(SHIKIMORI_URL + image?.original)
                ivImageBackground.setImageByURL(SHIKIMORI_URL + image?.original)
            }

            ivBackPressed.setOnClickListener {
                onBackPressed()
            }

            if (model.item.favoured == true) {
                ivFavorite.setImageResource(R.drawable.icon_favorite_true)
            } else {
                ivFavorite.setImageResource(R.drawable.icon_favorite_false)
            }

            ivFavorite.setOnClickListener {
                if (model.item.favoured == true) {
                    favoriteAction(
                        FavoriteAction.DELETE_FAVORITE(
                            linkedId = model.item.id,
                            linkedType = Type.PERSON
                        )
                    )
                    it.animShake {
                        model.item.favoured = false
                        ivFavorite.setImageResource(R.drawable.icon_favorite_false)
                    }
                } else if (model.item.favoured == null || model.item.favoured == false) {
                    favoriteAction(
                        FavoriteAction.CREATE_FAVORITE(
                            linkedId = model.item.id,
                            linkedType = Type.PERSON
                        )
                    )
                    it.animShake {
                        model.item.favoured = true
                        ivFavorite.setImageResource(R.drawable.icon_favorite_true)
                    }
                }
            }
        }
    }


    object ContainerPersonInfoDiffCallback : DiffUtil.ItemCallback<ContainerPersonInfo>() {
        override fun areItemsTheSame(
            oldItem: ContainerPersonInfo,
            newItem: ContainerPersonInfo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContainerPersonInfo,
            newItem: ContainerPersonInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}
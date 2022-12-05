package com.example.otaku.anime.details.persons.adapters.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutPersonInfoBinding
import com.example.otaku.utils.setImageByURL
import com.example.animator_domain.SHIKIMORI_URL

class ContainerPersonInfoAdapter(private val onBackPressed: () -> Unit) :
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
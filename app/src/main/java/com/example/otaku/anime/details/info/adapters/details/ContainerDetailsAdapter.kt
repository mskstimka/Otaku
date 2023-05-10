package com.example.otaku.anime.details.info.adapters.details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku_domain.models.details.AnimeDetailsEntity
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku.R
import com.example.otaku.anime.details.info.ui.DetailsFragment
import com.example.otaku.anime.details.info.ui.DetailsFragmentDirections
import com.example.otaku.databinding.LayoutDetailsInfoBinding
import com.example.otaku.utils.copyToClipboard
import com.example.otaku.utils.setImageByURL
import com.example.otaku.utils.toAnimePoster
import com.example.otaku_domain.*
import com.example.otaku_domain.models.user.status.RateStatus
import com.example.otaku_domain.models.user.status.UserRate

class ContainerDetailsAdapter(
    private val onBackPressed: () -> Unit,
    private val addFavorites: (id: AnimePosterEntity) -> Unit,
    private val deleteFavorites: (id: Int) -> Unit,
    private val checkIsFavorite: (id: Int) -> Boolean,
    private val updateOrCreateUserRate: (userRate: UserRate) -> Unit
) :
    ListAdapter<AnimeDetailsEntity, ContainerDetailsAdapter.ParentDetailsViewHolder>(
        ParentDetailsDiffCallback
    ) {

    private val genresAdapter by lazy { GenresAdapter() }

    var userRate = UserRate(episodes = 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentDetailsViewHolder {
        val binding =
            LayoutDetailsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        with(binding) {

            spinnerWatchStatus.apply {
                adapter = ArrayAdapter(
                    root.context,
                    R.layout.spinner_text, root.context.resources.getStringArray(R.array.statuses)
                )

                dropDownVerticalOffset = 30
                dropDownHorizontalOffset = 30

                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?, position: Int, id: Long
                    ) {

                        when (position) {
                            POSITION_COMPLETED -> {
                                userRate.status = RateStatus.COMPLETED.status
                                updateOrCreateUserRate(userRate)
                            }
                            POSITION_DROPPED -> {
                                userRate.status = RateStatus.DROPPED.status
                                updateOrCreateUserRate(userRate)
                            }
                            POSITION_WATCHING -> {
                                userRate.status = RateStatus.WATCHING.status
                                updateOrCreateUserRate(userRate)
                            }
                            POSITION_PLANNED -> {
                                userRate.status = RateStatus.PLANNED.status
                                updateOrCreateUserRate(userRate)
                            }
                            POSITION_ON_HOLD -> {
                                userRate.status = RateStatus.ON_HOLD.status
                                updateOrCreateUserRate(userRate)
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }

            rvGenre.adapter = genresAdapter
            rvGenre.layoutManager =
                LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)

            ivBackPressed.setOnClickListener {
                onBackPressed()
            }
        }

        return ParentDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentDetailsViewHolder, position: Int) =
        holder.bind(currentList[position])


    override fun getItemCount(): Int = currentList.size

    inner class ParentDetailsViewHolder(
        private val binding:
        LayoutDetailsInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AnimeDetailsEntity) = with(binding) {


            ivImageFranchises.setImageByURL("$SHIKIMORI_URL${item.image.original}")
            ivImageBackground.setImageByURL("$SHIKIMORI_URL${item.image.original}")

            ivImageFranchises.setOnClickListener {
                root.findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToScreenshotsFragment("$SHIKIMORI_URL${item.image.original}")
                )
            }
            tvTitle.text = item.name
            tvTitleRussian.text = item.russian

            exDescription.text = if (item.description != null) {
                HtmlCompat.fromHtml(item.description_html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                NOT_FOUND_TEXT
            }

            tvScore.text = item.score
            tvKind.text = item.kind

            tvDate.text = item.aired_on
            tvStatus.text = item.status
            tvStatus.setTextColor(Color.parseColor(item.statusColor))

            if (item.userRate != null) {
                ivFavorite.setImageResource(R.drawable.icon_favorite_true)
            }

//            if (checkIsFavorite(item.id)) {
//                addIsFavorite(tvFavorite, root.context, item.toAnimePoster(), false)
//            } else {
//                deleteIsFavorite(tvFavorite, root.context, item.id, false)
//            }
//
//
//            tvFavorite.setOnClickListener {
//                if (checkIsFavorite(item.id)) {
//                    deleteIsFavorite(tvFavorite, root.context, item.id, true)
//                } else {
//                    addIsFavorite(
//                        tvFavorite,
//                        root.context,
//                        item.toAnimePoster(),
//                        true
//                    )
//                }
//            }

            when (userRate.status) {
                RateStatus.COMPLETED.status -> {
                    spinnerWatchStatus.setSelection(POSITION_COMPLETED)
                }
                RateStatus.PLANNED.status -> {
                    spinnerWatchStatus.setSelection(POSITION_PLANNED)
                }
                RateStatus.DROPPED.status -> {
                    spinnerWatchStatus.setSelection(POSITION_DROPPED)
                }
                RateStatus.ON_HOLD.status -> {
                    spinnerWatchStatus.setSelection(POSITION_ON_HOLD)
                }
                RateStatus.WATCHING.status -> {
                    spinnerWatchStatus.setSelection(POSITION_WATCHING)
                }
            }

            val allEpisodes = if (item.episodes.toString() != DetailsFragment.ZERO_TEXT) {
                item.episodes.toString()
            } else {
                item.episodes_aired.toString()
            }

            tvEpisodeCount.text = "${userRate.episodes.toString()} / $allEpisodes"

            ivSettingAnime.setOnClickListener {
                showEpisodeInputDialog(userRate.episodes ?: 0, root.context) {
                    userRate.episodes = it
                    tvEpisodeCount.text = "${userRate.episodes.toString()} / $allEpisodes"
                    updateOrCreateUserRate(userRate)
                }
            }

            btnPlus.setOnClickListener {
                val lastEpisodes = userRate.episodes ?: 0
                userRate.episodes = lastEpisodes + 1
                updateOrCreateUserRate(userRate)
                tvEpisodeCount.text = "${userRate.episodes.toString()} / $allEpisodes"
            }


//            btnMinus.setOnClickListener {
//                if (userRate.episodes != 0 && userRate.episodes != null) {
//                    val lastEpisodes = userRate.episodes ?: 0
//                    userRate.episodes = lastEpisodes - 1
//                    updateOrCreateUserRate(userRate)
//                    tvEpisodeCount.text = userRate.episodes.toString()
//                }
//            }

        }
    }

    private fun showEpisodeInputDialog(
        currentEpisodeCount: Int,
        context: Context,
        action: (Int) -> Unit
    ) {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)

        val titleTextView = TextView(context).apply {
            text = context.getText(R.string.title_settings_episode)
            gravity = Gravity.CENTER
            textSize = 18f
            typeface = ResourcesCompat.getFont(context, R.font.open_sans_medium)
        }
        builder.setCustomTitle(titleTextView)
        val input = EditText(context).apply {
            gravity = Gravity.CENTER
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(currentEpisodeCount.toString())
            typeface = ResourcesCompat.getFont(context, R.font.open_sans_medium)
        }

        builder.setView(input)

        builder.setPositiveButton(context.getString(R.string.positive_button_settings_episode)) { dialog, _ ->
            val episodeCount = input.text.toString().toInt()
            action(episodeCount)
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun submitList(list: MutableList<AnimeDetailsEntity>?) {
        super.submitList(list)

        if (!list.isNullOrEmpty()) {
            val newUserRate = list.first().userRate
            if (newUserRate != null) {
                userRate = newUserRate
                userRate.targetType = "Anime"
                userRate.targetId = list.first().id.toLong()
            } else {
                userRate.targetType = "Anime"
                userRate.targetId = list.first().id.toLong()
            }

            genresAdapter.submitList(list.first().genres)
        }
    }


    object ParentDetailsDiffCallback : DiffUtil.ItemCallback<AnimeDetailsEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimeDetailsEntity,
            newItem: AnimeDetailsEntity
        ): Boolean {
            return oldItem.userRate == newItem.userRate
        }

        override fun areContentsTheSame(
            oldItem: AnimeDetailsEntity,
            newItem: AnimeDetailsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
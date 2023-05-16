package com.example.otaku.user.adapters.stats

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.otaku.databinding.LayoutUserStatsBinding
import com.example.otaku.user.adapters.stats.models.UserStatsContainer
import com.example.otaku_domain.models.user.status.RateStatus

class UserStatsAdapter(userId: Long) :
    ListAdapter<UserStatsContainer, UserStatsAdapter.UserStatsViewHolder>(
        UserStatsInfoDiffCallback
    ) {

    private val statsAdapter by lazy { StatsAdapter(userId = userId) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserStatsViewHolder {
        val binding =
            LayoutUserStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvStatuses.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        binding.rvStatuses.adapter = statsAdapter
        return UserStatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserStatsViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<UserStatsContainer>?) {
        statsAdapter.submitList(list?.first()?.statsList ?: emptyList())
        super.submitList(list)
    }

    fun startAnimation(view: View) {
        val translationX =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, -view.width.toFloat()).apply {
                duration = 2000
            }
        val rotationY = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 0f, 180f).apply {
            duration = 400
        }
        val translationXReverse =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -view.width.toFloat(), 0f).apply {
                duration = 2000
            }
        val rotationYReverse = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 180f, 0f).apply {
            duration = 400
        }


        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(translationX, rotationY, translationXReverse, rotationYReverse)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // При окончании анимации, запустить ее снова
                startAnimation(view)
            }
        })

        animatorSet.start()
    }

    inner class UserStatsViewHolder(
        private val binding:
        LayoutUserStatsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserStatsContainer) = with(binding) {
            with(model) {
                if (details.id == 577000L) {
                    tvTitle.setPadding(0, 50, 0, 0)
                    ivCreatorMark.visibility = View.VISIBLE
                    startAnimation(ivCreatorMark)
                }
                val completed = statsList.find { it.type == RateStatus.COMPLETED }?.count ?: 0
                tvCompletedCount.text = completed.toString()
                tvOtherCount.text = others.toString()
                pbAnime.max = completed + others
                pbAnime.progress = others
                pbAnime.secondaryProgress = completed
            }
        }
    }

    object UserStatsInfoDiffCallback : DiffUtil.ItemCallback<UserStatsContainer>() {
        override fun areItemsTheSame(
            oldItem: UserStatsContainer,
            newItem: UserStatsContainer
        ): Boolean {
            return oldItem.details.id == newItem.details.id
        }

        override fun areContentsTheSame(
            oldItem: UserStatsContainer,
            newItem: UserStatsContainer
        ): Boolean {
            return oldItem == newItem
        }
    }

}
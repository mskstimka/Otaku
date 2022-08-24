package com.example.animator.utils

import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import com.example.animator.R

object AnimatorUtils {
    private var animation = true


    fun toCloseView(context: Context, vararg views: View) {
        if (animation) {
            views.forEach { view ->
                AnimatorInflater.loadAnimator(
                    context, R.animator.close_view
                ).apply {
                    setTarget(view)
                    start()
                }
            }
        }
        animation = false
    }


    fun toStartView(context: Context, vararg views: View) {
        if (!animation) {
            views.forEach { view ->
                AnimatorInflater.loadAnimator(
                    context, R.animator.start_view
                ).apply {
                    setTarget(view)
                    start()
                }
            }
        }
        animation = true
    }

}
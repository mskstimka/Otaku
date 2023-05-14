package com.example.otaku.utils

import android.view.View
import android.view.animation.*


fun View.animShake(action: () -> Unit) {
    val rotateAnimation = RotateAnimation(
        -5f,
        5f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 70
        repeatCount = 5
        repeatMode = Animation.REVERSE
        interpolator = LinearInterpolator()
    }

    val translateAnimation = TranslateAnimation(-10f, 10f, 0f, 0f).apply {
        duration = 70
        repeatCount = 5
        repeatMode = Animation.REVERSE
        interpolator = LinearInterpolator()
    }
    val animShake = AnimationSet(true).apply {
        addAnimation(rotateAnimation)
        addAnimation(translateAnimation)
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                action()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }

    this.startAnimation(animShake)
}
package com.animation.animpresseffect

import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import org.jetbrains.annotations.NotNull

/**
 * PressAnimation implements elastic animations for android views or view groups.
 **/
class PressAnimation(private val view: View) {

    var scale = 0.9f
    var duration = 400
    var listener: ViewPropertyAnimatorListener? = null
    var animationEndListener: AnimationEndListener? = null

    fun duration(@NotNull duration: Int): PressAnimation = apply { this.duration = duration }

    fun scale(@NotNull scale: Float): PressAnimation = apply { this.scale = scale }

    fun setListener(listener: ViewPropertyAnimatorListener): PressAnimation =
        apply { this.listener = listener }

    fun onFinishListener(finishListener: AnimationEndListener): PressAnimation =
        apply { this.animationEndListener = finishListener }

    fun onFinishListener(run: () -> Unit): PressAnimation = apply {
        this.animationEndListener = object : AnimationEndListener {
            override fun onAnimationEnd() {
                run()
            }
        }
    }

    /**
     * Starts animation.
     **/
    fun animate() {
        val animatorCompat = ViewCompat.animate(view)
            .setDuration(duration.toLong()).scaleX(scale).scaleY(scale)
            .setInterpolator(CycleInterpolator(0.5f))
        this.listener?.let { animatorCompat.setListener(it) }
        this.animationEndListener?.let {
            animatorCompat.setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) = it.onAnimationEnd()
                override fun onAnimationCancel(view: View?) = Unit
                override fun onAnimationStart(view: View?) = Unit
            })
        }

        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                val nextChild = view.getChildAt(index)
                ViewCompat.animate(nextChild)
                    .setDuration(duration.toLong()).scaleX(scale).scaleY(scale)
                    .setInterpolator(CycleInterpolator(0.5f))
                    .withLayer()
                    .start()
            }
        }
        animatorCompat.withLayer().start()
    }
}

/**
 * Listens to animation end
 **/
interface AnimationEndListener {

    /**
     * Triggers click action on animation end
     **/
    fun onAnimationEnd()
}

/**
 * Extension function for views
 **/
fun View.pressAnimation(
    scale: Float, duration: Int, listener: AnimationEndListener
): PressAnimation {
    return PressAnimation(this).scale(scale).duration(duration)
        .onFinishListener(listener)
}

/**
 * Creates Press Animation by kotlin
 **/
@Suppress("unused")
fun pressAnimation(view: View, block: PressAnimation.() -> Unit): PressAnimation =
    PressAnimation(view).apply(block)

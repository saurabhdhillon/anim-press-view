package com.animation.animpresseffect

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class PressEffectContainerLayout(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    private var scale = 0.9f
    private var duration = 400

    private var onClickListener: OnClickListener? = null
    private var animationEndListener: AnimationEndListener? = null

    init {
        attrs.let { a ->
            val typedArray =
                context.obtainStyledAttributes(a, R.styleable.PressEffectContainerLayout, 0, 0)
            scale = typedArray.getFloat(R.styleable.PressEffectContainerLayout_layout_scale, scale)
            duration =
                typedArray.getInt(R.styleable.PressEffectContainerLayout_layout_duration, duration)
            typedArray.recycle()
        }
        init()
    }

    private fun init() {
        super.setOnClickListener {
            if (this.scaleX == 1f) {
                pressAnimation(this) {
                    scale(this@PressEffectContainerLayout.scale)
                    duration(this@PressEffectContainerLayout.duration)
                    onFinishListener { processClickAction() }
                }.animate()
            }
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    fun setAnimationEndListener(listener: AnimationEndListener) {
        this.animationEndListener = listener
    }

    private fun processClickAction() {
        this.onClickListener?.onClick(this)
        this.animationEndListener?.onAnimationEnd()
    }
}
package com.animation.animpresseffect

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class PressEffectButton(
    context: Context,
    attrs: AttributeSet?
) : AppCompatButton(context, attrs) {

    private var scale = 0.9f
    private var duration = 400

    private var onClickListener: OnClickListener? = null
    private var animationEndListener: AnimationEndListener? = null

    init {
        attrs.let { a ->
            val typedArray = context.obtainStyledAttributes(a, R.styleable.PressEffectButton, 0, 0)
            scale = typedArray.getFloat(R.styleable.PressEffectButton_button_scale, scale)
            duration = typedArray.getInt(R.styleable.PressEffectButton_button_duration, duration)
            typedArray.recycle()
        }
        init()
    }

    private fun init() {
        super.setOnClickListener {
            if (this.scaleX == 1f) {
                pressAnimation(this) {
                    scale(this@PressEffectButton.scale)
                    duration(this@PressEffectButton.duration)
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
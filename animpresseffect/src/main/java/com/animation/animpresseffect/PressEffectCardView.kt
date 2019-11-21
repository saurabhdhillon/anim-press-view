package com.animation.animpresseffect

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class PressEffectCardView(
    context: Context,
    attrs: AttributeSet?
) : CardView(context, attrs) {

    private var scale = 0.9f
    private var duration = 400

    private var onClickListener: OnClickListener? = null
    private var animationEndListener: AnimationEndListener? = null

    init {
        attrs.let { a ->
            val typedArray =
                context.obtainStyledAttributes(a, R.styleable.PressEffectCardView, 0, 0)
            scale = typedArray.getFloat(R.styleable.PressEffectCardView_card_scale, scale)
            duration = typedArray.getInt(R.styleable.PressEffectCardView_card_duration, duration)
            typedArray.recycle()
        }
        init()
    }

    private fun init() {
        super.setOnClickListener {
            if (this.scaleX == 1f) {
                pressAnimation(this) {
                    scale(this@PressEffectCardView.scale)
                    duration(this@PressEffectCardView.duration)
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
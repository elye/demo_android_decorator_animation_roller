package com.elyeproj.animatedecorator

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock

class ShoeLaceAnimatedDecoratorDrawable(override val height: Int,
                                        override val width: Int) : AnimatedDecorator.AnimatedDecoratorDrawable {

    private var lastTimeAnimated = 0L
    private var deltaTime = 0L

    private val extra = 8

    private val rectUp = RectF(0f, 0f, width.toFloat(), height.toFloat())
    private val paint = Paint()
            .apply { this.color = Color.GRAY }
            .apply { this.style = Paint.Style.STROKE }
            .apply { this.isAntiAlias = true }
            .apply { this.strokeWidth = 8f }

    private val startCircle = 0f
    private val quarterCircle = 90f
    private val halfCircle = 180f
    private val threeQuarterCircle = 270f
    private val fullCircle = 360f

    private var startString = StringMoving(startCircle, fullCircle, startCircle)
    private val segment = 110
    private var stringMovement = 0f
    private val withSegment = (width / (segment - 1) / 2).toFloat()
    private val top = 0 - extra.toFloat()
    private val bottom = height + extra.toFloat()

    companion object {
        const val ANIMATE_DELAY = 1L
    }

    override fun draw(canvas: Canvas) {

        lastTimeAnimated = SystemClock.uptimeMillis()

        if (deltaTime + ANIMATE_DELAY < lastTimeAnimated) {
            deltaTime = lastTimeAnimated
            val moving = startString.value
            stringMovement = moving.first
        }

        for (i in 1..segment / 2) {
            drawArc(i, 20f, top, bottom)

            val individualMovement = getIndividualMovement(stringMovement, i)
            if (individualMovement <= halfCircle) {
                canvas.drawArc(rectUp, threeQuarterCircle, -individualMovement, false, paint)
            } else {
                canvas.drawArc(rectUp, quarterCircle, fullCircle - individualMovement, false, paint)
            }
        }

    }

    private fun getIndividualMovement(stringMovement: Float, part: Int): Float {
        val returnValue = stringMovement + part * fullCircle/segment
        return if (returnValue < fullCircle) returnValue else returnValue - fullCircle
    }

    private fun drawArc(count: Int, withSegment: Float, top: Float, bottom: Float) {
        rectUp.set((count - 1) * withSegment, top, (count + 3) * withSegment, bottom)
    }

    class StringMoving(private val start: Float, private val end: Float, private var current: Float) {

        val value: Pair<Float, Boolean>
            get() {
                val isReset = incrementCurrent()
                return Pair(current, isReset)
            }

        private fun incrementCurrent(): Boolean {
            current += 6
            if (current > end) {
                current = start
                return true
            }
            return false
        }

    }
}
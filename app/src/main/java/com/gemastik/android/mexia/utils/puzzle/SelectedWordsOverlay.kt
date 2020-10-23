package com.gemastik.android.mexia.utils.puzzle

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.gemastik.android.mexia.R

class SelectedWordsOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): View(context, attrs, defStyle) {

    var attachedGrid: Array<Array<TextView>> = emptyArray()
    private val words: MutableList<Word> = mutableListOf()
    private val positionOnScreen = IntArray(2)
    private val startPoint = IntArray(2)
    private val endPoint = IntArray(2)
    var previewStartCell: Cell? = null
    var previewEndCell: Cell? = null
        set(value) {
            field = value
            if (value != null) {
                calculatePreviewPositions()
            } else {
                previewAnimator = null
                previewStartX = null
                previewStartY = null
                previewEndX = null
                previewEndY = null
                previewAnimator?.cancel()
                previewAnimator = null
                invalidate()
            }
        }
    private var previewAnimator: ValueAnimator? = null
    private var previewStartX: Float? = null
    private var previewStartY: Float? = null
    private var previewEndX: Float? = null
    private var previewEndY: Float? = null

    var highlightColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            paint.color = value
            paint.alpha = (255 * highlightAlpha).toInt()
        }
    var highlightWidth: Float = 48f
        set (value) {
            field = value
            paint.strokeWidth = value
            previewPaint.strokeWidth = value
        }
    var highlightAlpha: Float = 0.5f
        set (value) {
            field = value
            paint.alpha = (255 * value).toInt()
            previewPaint.alpha = (255 * value).toInt()
        }

    private val paint = Paint().apply {
        color = highlightColor
        isAntiAlias = true
        strokeWidth = highlightWidth
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
        alpha = (255 * highlightAlpha).toInt()
    }

    private val previewPaint = Paint().apply {
        color = Color.parseColor("#808080")
        isAntiAlias = true
        strokeWidth = highlightWidth
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
        alpha = (255 * highlightAlpha).toInt()
    }

    init {
        setWillNotDraw(false)

        context.theme.obtainStyledAttributes(attrs, R.styleable.SelectedWordsOverlay, 0, 0).apply {
            try {
                highlightColor = getColor(R.styleable.SelectedWordsOverlay_highlightColor, Color.TRANSPARENT)
                highlightWidth = getDimension(R.styleable.SelectedWordsOverlay_highlightWidth, 48f)
                highlightAlpha = getDimension(R.styleable.SelectedWordsOverlay_highlightAlpha, 0.5f)
            } finally {
                recycle()
            }
        }
    }

    fun addWord(vararg word: Word) {
        words.addAll(word)
        invalidate()
    }

    fun clearWords() {
        words.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        parent.requestDisallowInterceptTouchEvent(true)
        getLocationOnScreen(positionOnScreen)

        words.forEach { word ->
            attachedGrid[word.start.row][word.start.col].getLocationOnScreen(startPoint)
            attachedGrid[word.end.row][word.end.col].getLocationOnScreen(endPoint)

            val startX = startPoint[0] + (attachedGrid[word.start.row][word.start.col].width / 2f) - positionOnScreen[0]
            val startY = startPoint[1] + (attachedGrid[word.start.row][word.start.col].height / 2f) - positionOnScreen[1]
            val endX = endPoint[0] + (attachedGrid[word.end.row][word.end.col].width / 2f) - positionOnScreen[0]
            val endY = endPoint[1] + (attachedGrid[word.end.row][word.end.col].height / 2f) - positionOnScreen[1]

            canvas?.drawLine(startX, startY, endX, endY, paint)
        }

        if (previewStartCell != null && previewEndCell != null) {
            canvas?.drawLine(previewStartX!!, previewStartY!!, previewEndX!!, previewEndY!!, previewPaint)
        }
    }

    private fun calculatePreviewPositions() {
        if (previewStartCell == null || previewEndCell == null) {
            return
        }
        getLocationOnScreen(positionOnScreen)

        attachedGrid[previewStartCell!!.row][previewStartCell!!.col].getLocationOnScreen(startPoint)
        attachedGrid[previewEndCell!!.row][previewEndCell!!.col].getLocationOnScreen(endPoint)

        val startX =
            startPoint[0] + (attachedGrid[previewStartCell!!.row][previewStartCell!!.col].width / 2f) - positionOnScreen[0]
        val startY =
            startPoint[1] + (attachedGrid[previewStartCell!!.row][previewStartCell!!.col].height / 2f) - positionOnScreen[1]
        val endX =
            endPoint[0] + (attachedGrid[previewEndCell!!.row][previewEndCell!!.col].width / 2f) - positionOnScreen[0]
        val endY =
            endPoint[1] + (attachedGrid[previewEndCell!!.row][previewEndCell!!.col].height / 2f) - positionOnScreen[1]

        val propertyStartX = PropertyValuesHolder.ofFloat("SX", previewStartX ?: startX, startX)
        val propertyStartY = PropertyValuesHolder.ofFloat("SY", previewStartY ?: startY, startY)
        val propertyEndX = PropertyValuesHolder.ofFloat("EX", previewEndX ?: endX, endX)
        val propertyEndY = PropertyValuesHolder.ofFloat("EY", previewEndY ?: endY, endY)

        previewAnimator?.cancel()

        previewAnimator = ValueAnimator().apply {
            duration = 50
            interpolator = FastOutSlowInInterpolator()
            setValues(propertyStartX, propertyStartY, propertyEndX, propertyEndY)
            addUpdateListener { animation ->
                previewStartX = animation.getAnimatedValue("SX") as Float?
                previewStartY = animation.getAnimatedValue("SY") as Float?
                previewEndX = animation.getAnimatedValue("EX") as Float?
                previewEndY = animation.getAnimatedValue("EY") as Float?
                invalidate()
            }
        }
        previewAnimator?.start()
    }
}
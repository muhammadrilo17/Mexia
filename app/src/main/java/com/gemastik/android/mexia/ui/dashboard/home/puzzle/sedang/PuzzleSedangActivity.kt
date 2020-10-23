package com.gemastik.android.mexia.ui.dashboard.home.puzzle.sedang

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.akhir.AkhirViewModel
import com.gemastik.android.mexia.utils.puzzle.Cell
import com.gemastik.android.mexia.utils.puzzle.SelectedWordsOverlay
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_puzzle_akhir.*

class PuzzleSedangActivity : AppCompatActivity() {
    private lateinit var textViewGrid: Array<Array<TextView>>
    private lateinit var viewModel: SedangViewModel
    private val rowSize = 6
    private val columnSize = 6

    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private var firstTouchedCell: Cell? = null
    private var lastTouchedCell: Cell? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle_sedang)

        findViewById<GridLayout>(R.id.verticalContainer).apply {
            rowCount = rowSize
            columnCount = columnSize
        }

        val dialog = ProgressDialog.show(this, "", "Please wait...", true)
        createTextGrid()
        viewModel = ViewModelProvider(this, factory)[SedangViewModel::class.java]
        viewModel.getAll4Words().observe(this, Observer { Words ->
            if (Words != null){
                val temp: List<String> = Words.map { it.name }
                val words = listOf(temp[0], temp[1], temp[2], temp[3], temp[4])
                if (viewModel.wordSearch.value == null) {
                    viewModel.generateWordSearch(rowSize, columnSize, words)
                }
            }
            dialog.dismiss()
        })



        viewModel.wordSearch.observe(this, Observer { (letterGrid, words) ->
            if (letterGrid.size != textViewGrid.size) return@Observer

            val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
            chipGroup.removeAllViews()

            val overlay = findViewById<SelectedWordsOverlay>(R.id.selectedWordsOverlay)
            overlay.clearWords()
            var i = 0
            words.forEach { word ->
                val (wordText, _, _, _, found) = word
                Chip(this).apply {
                    text = wordText
                    isCheckable = true
                    isChecked = found
                    chipGroup.addView(this)
                    setOnTouchListener { _, _ -> true }
                }

                if (found) {
                    ++i
                    overlay.addWord(word)
                }
                if (i==words.size){
                    checking.setImageResource(R.drawable.ic_checked)
                    button.setBackgroundResource(R.drawable.bg_lanjut_next)
                }
            }
        })

        viewModel.textGrid.observe(this, Observer { letterGrid ->
            letterGrid.forEachIndexed { i, row ->
                row.forEachIndexed { j, col ->
                    textViewGrid[i][j].text = col.toString()
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    private fun createTextGrid() {
        val grid = findViewById<GridLayout>(R.id.verticalContainer)

        textViewGrid =
            Array(rowSize) { i ->
                Array(columnSize) { j ->
                    createTextCell(i, j, grid)
                }
            }

        findViewById<SelectedWordsOverlay>(R.id.selectedWordsOverlay).attachedGrid = textViewGrid

        grid.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (viewModel.verifySelection(firstTouchedCell, lastTouchedCell)) {
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                }

                lastTouchedCell = null
                firstTouchedCell = null

                findViewById<SelectedWordsOverlay>(R.id.selectedWordsOverlay).apply {
                    previewEndCell = null
                    previewStartCell = null
                }
                return@setOnTouchListener true
            }

            val x = Math.round(event.x)
            val y = Math.round(event.y)

            grid.children.forEach { child ->
                if (x > child.left && x < child.right && y > child.top && y < child.bottom) {
                    val overlay = findViewById<SelectedWordsOverlay>(R.id.selectedWordsOverlay)
                    if (lastTouchedCell !== child.tag) {
                        child.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        overlay.previewEndCell = child.tag as Cell
                    }

                    if (firstTouchedCell == null) {
                        firstTouchedCell = child.tag as Cell
                        overlay.previewStartCell = firstTouchedCell
                    } else {
                        lastTouchedCell = child.tag as Cell
                    }
                }
            }

            true
        }
    }

    private fun createTextCell(row: Int, col: Int, gridLayout: GridLayout): TextView = TextView(this).apply {
        gridLayout.addView(this)
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        tag = Cell(row, col)

        layoutParams = GridLayout.LayoutParams().apply {
            height = GridLayout.LayoutParams.WRAP_CONTENT
            width = GridLayout.LayoutParams.WRAP_CONTENT
            setGravity(Gravity.CENTER or Gravity.FILL)
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
        }
    }
}
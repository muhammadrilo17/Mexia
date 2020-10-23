package com.gemastik.android.mexia.utils.puzzle

import java.util.*
import kotlin.random.Random

class WordSearchFactory(var row: Int = 5, var column: Int = 5) {
    private val randomInstance = Random(Date().time)
    private val letterPool = 'A'..'Z'
    private val noReverseDirections =
        arrayOf(PlacementDirection.HORIZONTAL, PlacementDirection.VERTICAL, PlacementDirection.DIAGONAL)

    fun generateWordSearch(words: List<String>, reversible: Boolean = true): WordSearch {
        val output = Array(row) { Array(column) { Char.MIN_VALUE } }
        val placedWords = mutableListOf<Word>()

        val sortedWords = words
            .sortedByDescending(String::length)
            .map(String::toUpperCase)

        sortedWords.forEach { word ->
            var placed = false
            while (!placed) {
                val start = Cell(
                    (0 until row).random(randomInstance),
                    (0 until column).random(randomInstance)
                )

                val placement =
                    if (reversible) PlacementDirection.values().random(randomInstance) else noReverseDirections.random()

                if (!checkPlacement(word, start, placement)) continue
                if (checkOverlaps(output, word, start, placement)) {
                    placed = true
                    placedWords.add(placeWord(output, word, start, placement))
                }
            }
        }

        output.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell == Char.MIN_VALUE) {
                    output[y][x] = letterPool.random(randomInstance)
                }
            }
        }

        return WordSearch(output, placedWords)
    }

    private fun checkPlacement(word: String, start: Cell, placement: PlacementDirection): Boolean {
        val (row, col) = start
        return when (placement) {
            PlacementDirection.VERTICAL -> row + word.length < row
            PlacementDirection.VERTICAL_REVERSE -> row - word.length > 0
            PlacementDirection.HORIZONTAL -> col + word.length < column
            PlacementDirection.HORIZONTAL_REVERSE -> col - word.length > 0
            PlacementDirection.DIAGONAL -> row + word.length < row && col + word.length < column
            PlacementDirection.DIAGONAL_REVERSE -> row - word.length > 0 && col - word.length > 0
        }
    }
    private fun checkOverlaps(
        grid: Array<Array<Char>>,
        word: String,
        start: Cell,
        placement: PlacementDirection
    ): Boolean {
        word.forEachIndexed { index, c ->
            val (row, col) = start.plus(index, placement)

            if (grid[row][col] != Char.MIN_VALUE && grid[row][col] != c) {
                return false
            }
        }

        return true
    }

    private fun placeWord(grid: Array<Array<Char>>, word: String, start: Cell, placement: PlacementDirection): Word {
        word.forEachIndexed { index, c ->
            val (row, col) = start.plus(index, placement)

            grid[row][col] = c
        }
        return Word(word, start, start.plus(word.length - 1, placement), placement)
    }
}
package com.gemastik.android.mexia.utils.puzzle

data class Cell (val row: Int, val col: Int) {

    fun plus(amount: Int, direction: PlacementDirection): Cell {
        val nCol = when (direction) {
            PlacementDirection.HORIZONTAL, PlacementDirection.DIAGONAL -> col + amount
            PlacementDirection.HORIZONTAL_REVERSE, PlacementDirection.DIAGONAL_REVERSE -> col - amount
            else -> col
        }

        val nRow = when (direction) {
            PlacementDirection.VERTICAL, PlacementDirection.DIAGONAL -> row + amount
            PlacementDirection.VERTICAL_REVERSE, PlacementDirection.DIAGONAL_REVERSE -> row - amount
            else -> row
        }

        return Cell(nRow, nCol)
    }
}
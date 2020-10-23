package com.gemastik.android.mexia.ui.dashboard.home.puzzle.mudah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.utils.puzzle.Cell
import com.gemastik.android.mexia.utils.puzzle.WordSearch

class MudahViewModel(private val repository: Repository): ViewModel() {
    private val words = listOf("aku", "ibu", "dia")

    private val mWordSearch = MutableLiveData<WordSearch>()
    val wordSearch: LiveData<WordSearch> = mWordSearch

    private val _textGrid = MutableLiveData<Array<Array<Char>>>()
    val textGrid: LiveData<Array<Array<Char>>> = _textGrid

    var allowReverseWords = true

    fun generateWordSearch(row: Int, column: Int) {
        mWordSearch.value = repository.generator(row, column).generateWordSearch(words, allowReverseWords)
        _textGrid.value = mWordSearch.value!!.grid
    }

    fun verifySelection(start: Cell?, end: Cell?): Boolean {
        val search = wordSearch.value!!
        val words = search.words
        words.forEach { word ->
            if (word.start == start && word.end == end) {
                val newList = search.words.map {
                    if (it.text == word.text) it.copy(found = true) else it
                }

                mWordSearch.value = search.copy(words = newList)
                return true
            }
        }
        return false
    }
}
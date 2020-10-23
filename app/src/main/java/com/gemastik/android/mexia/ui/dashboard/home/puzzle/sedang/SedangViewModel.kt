package com.gemastik.android.mexia.ui.dashboard.home.puzzle.sedang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.repository.remote.entity.Api4WordEntity
import com.gemastik.android.mexia.utils.puzzle.Cell
import com.gemastik.android.mexia.utils.puzzle.WordSearch

class SedangViewModel(private val repository: Repository): ViewModel() {

    fun getAll4Words(): LiveData<List<Api4WordEntity>>{
        return repository.getAll4Word()
    }

    private val mWordSearch = MutableLiveData<WordSearch>()
    val wordSearch: LiveData<WordSearch> = mWordSearch

    private val _textGrid = MutableLiveData<Array<Array<Char>>>()
    val textGrid: LiveData<Array<Array<Char>>> = _textGrid

    var allowReverseWords = true

    fun generateWordSearch(row: Int, column: Int, words: List<String>) {
        mWordSearch.value = repository.generator(row, column).generateWordSearch(words, allowReverseWords)
        _textGrid.value = mWordSearch.value?.grid
    }

    fun verifySelection(start: Cell?, end: Cell?): Boolean {
        val search = wordSearch.value
        val words = search?.words
        words?.forEach { word ->
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
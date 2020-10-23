package com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf

import androidx.lifecycle.ViewModel
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.DummyAlphabet

class AlphabetViewModel(private val repository: Repository): ViewModel() {
    fun getAlphabet(): List<AlphabetEntity> {
        return repository.getAlphabet()
    }
}
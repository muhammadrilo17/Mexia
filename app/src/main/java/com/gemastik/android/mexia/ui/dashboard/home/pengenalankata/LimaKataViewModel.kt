package com.gemastik.android.mexia.ui.dashboard.home.pengenalankata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity

class LimaKataViewModel(private val repository: Repository): ViewModel() {
    fun getAllWord(): LiveData<List<Api5WordEntity>>{
        return repository.getAll5Word()
    }
}
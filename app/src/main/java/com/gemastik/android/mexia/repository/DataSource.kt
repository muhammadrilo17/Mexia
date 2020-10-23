package com.gemastik.android.mexia.repository

import androidx.lifecycle.LiveData
import com.gemastik.android.mexia.repository.remote.entity.Api4WordEntity
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetEntity
import com.gemastik.android.mexia.utils.puzzle.WordSearchFactory
import com.gemastik.android.mexia.vo.Resource

interface DataSource {
    fun generator(row: Int, column: Int): WordSearchFactory
    fun getAll5Word(): LiveData<List<Api5WordEntity>>
    fun getAll4Word(): LiveData<List<Api4WordEntity>>
    fun getAlphabet(): List<AlphabetEntity>
    fun getUserByEmail(email: String): LiveData<ApiUserEntity>
    fun registerUser(user: ApiUserEntity)
}
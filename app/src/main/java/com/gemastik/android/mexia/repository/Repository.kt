package com.gemastik.android.mexia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.android.mexia.repository.remote.NetworkDataSource
import com.gemastik.android.mexia.repository.remote.entity.Api4WordEntity
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.DummyAlphabet
import com.gemastik.android.mexia.utils.puzzle.WordSearchFactory

class Repository private constructor(
    private val networkDataSource: NetworkDataSource
): DataSource {
    companion object{
        @Volatile
        private var instance: Repository? = null

        fun getInstance(networkDataSource: NetworkDataSource): Repository =
            instance?: synchronized(this){
                instance?: Repository(networkDataSource)
            }
    }

    override fun generator(row: Int, column: Int): WordSearchFactory {
        return WordSearchFactory(row, column)
    }

    override fun getAll5Word(): LiveData<List<Api5WordEntity>> {
        return networkDataSource.getAll5Word()
    }

    override fun getAll4Word(): LiveData<List<Api4WordEntity>> {
        return networkDataSource.getAll4Word()
    }

    override fun getUserByEmail(email: String): LiveData<ApiUserEntity> {
      return networkDataSource.getUserByEmail(email)
    }

    override fun getAlphabet(): List<AlphabetEntity> {
        return DummyAlphabet.generateAlphabet()
    }

    override fun registerUser(user: ApiUserEntity) {
        networkDataSource.registerUser(user)
    }

}
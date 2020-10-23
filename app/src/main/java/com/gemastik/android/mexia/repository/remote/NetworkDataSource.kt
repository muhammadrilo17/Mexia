package com.gemastik.android.mexia.repository.remote

import androidx.lifecycle.LiveData
import com.gemastik.android.mexia.repository.remote.entity.Api4WordEntity
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity

class NetworkDataSource private constructor(private val network: NetworkHelper){

    companion object {
        @Volatile
        private var instance: NetworkDataSource? = null
        fun getInstance(api: NetworkHelper): NetworkDataSource {
            return instance?: synchronized(this) {
                instance?: NetworkDataSource(api)
            }
        }
    }

    fun getAll5Word(): LiveData<List<Api5WordEntity>>{
        return network.getAll5Word()
    }

    fun getUserByEmail(email: String): LiveData<ApiUserEntity>{
        return network.getUserByEmail(email)
    }

    fun registerUser(user: ApiUserEntity){
        network.registerUser(user)
    }

    fun getAll4Word(): LiveData<List<Api4WordEntity>>{
        return network.getAll4Word()
    }
}
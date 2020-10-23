package com.gemastik.android.mexia.utils

import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.repository.remote.NetworkDataSource
import com.gemastik.android.mexia.repository.remote.NetworkHelper

object Injection {
    fun provideRepository(): Repository {
        val networkDataSource = NetworkDataSource.getInstance(NetworkHelper())
        return Repository.getInstance(networkDataSource)
    }
}
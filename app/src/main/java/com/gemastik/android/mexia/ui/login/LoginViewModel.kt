package com.gemastik.android.mexia.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity

class LoginViewModel(private val repository: Repository): ViewModel() {
    fun getUserByEmail(email: String): LiveData<ApiUserEntity>{
        return repository.getUserByEmail(email)
    }
    fun registerUser(user: ApiUserEntity){
        repository.registerUser(user)
    }
}
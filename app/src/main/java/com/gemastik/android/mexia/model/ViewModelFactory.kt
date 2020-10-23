package com.gemastik.android.mexia.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.repository.Repository
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetViewModel
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.LimaKataViewModel
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.akhir.AkhirViewModel
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.mudah.MudahViewModel
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.sedang.SedangViewModel
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.sulit.SulitViewModel
import com.gemastik.android.mexia.ui.login.LoginViewModel
import com.gemastik.android.mexia.utils.Injection
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance?: synchronized(this){
                instance?: ViewModelFactory(Injection.provideRepository())
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MudahViewModel::class.java) -> {
                MudahViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SedangViewModel::class.java) -> {
                SedangViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SulitViewModel::class.java) -> {
                SulitViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AkhirViewModel::class.java) -> {
                AkhirViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AlphabetViewModel::class.java) -> {
                AlphabetViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LimaKataViewModel::class.java) -> {
                LimaKataViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
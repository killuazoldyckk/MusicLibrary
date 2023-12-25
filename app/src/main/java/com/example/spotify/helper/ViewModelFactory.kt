package com.example.spotify.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotify.ui.insert.FavouriteAddUpdateViewModel
import com.example.spotify.ui.library.LibraryViewModel

class ViewModelFactory private constructor(private val mApplication: Application ): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if(INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return  INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)){
            return LibraryViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(FavouriteAddUpdateViewModel::class.java)){
            return FavouriteAddUpdateViewModel(mApplication) as T
        }

        throw java.lang.IllegalArgumentException("Unknown View Model class ${modelClass.name}")
    }
}
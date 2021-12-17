package com.infnet.thebeatles.telas.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory(private val finalScore: Int, private val boolean: Boolean) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(finalScore, boolean) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
package com.infnet.thebeatles.telas.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(finalScore: Int, resultBoolean: Boolean) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean>
        get() = _result

    //livedata para o  eventPlayAgain

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        _score.value =
            finalScore
        checkResult(resultBoolean)

    }

    fun checkResult(resultBoolean: Boolean) {

        if (resultBoolean) {
            //vitoria
            _result.value = resultBoolean
            Log.i("resultGame", "Victory!")
        } else {

            _result.value = resultBoolean
            Log.i("resultGame", "Defeat!")
        }

    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true

    }

    fun onPlayAgainFinish() {
        _eventPlayAgain.value = false
    }

}
package com.andgp.usaa_exercise.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andgp.usaa_exercise.data.DataSourceRepository
import com.andgp.usaa_exercise.data.DataStatus.ErrorResponse
import com.andgp.usaa_exercise.data.DataStatus.Success
import com.andgp.usaa_exercise.data.local.Challenge
import com.andgp.usaa_exercise.isCollectionPassed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
class MainViewModel(application: Application) : ViewModel() {
    /**
     * viewModelJob allows us to cancel all coroutines started but this viewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Using two different approaches for the communication with the UI using data binding.
    // Observables from the MainActivity
    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus = _loadingStatus

    // Observables from the activity_main.xml
    private val _collectionPassed = MutableLiveData<String>()
    val collectionPassed = _collectionPassed

    private val dataSourceRepository: DataSourceRepository = DataSourceRepository(application)

    fun initComponents() {
        uiScope.launch {
            dataSourceRepository.nukeTable()
        }
    }

    fun tryObtainData() {
        uiScope.launch {
            _loadingStatus.value = true
            dataSourceRepository.tryObtainData { dataStatus ->
                when (dataStatus) {
                    is Success -> {
                        _collectionPassed.value = if (dataStatus.result.isCollectionPassed()) {
                            "Success!!!"
                        } else {
                            "Fail"
                        }
                    }
                    is ErrorResponse -> {
                        _collectionPassed.value = dataStatus.message
                    }
                }
                _loadingStatus.value = false
            }
        }
    }

    fun isCollectionPassed(result: List<Challenge>): Boolean {
        var correctAnswers = 0
        result.forEach { challenge ->
            Timber.d(challenge.toString())
            if (challenge.correct) correctAnswers++
        }
        Timber.d("Correct Answers: $correctAnswers, total questions: ${result.size}, Percentage: ${(correctAnswers.toDouble() / result.size) * 100}")
        return ((correctAnswers.toDouble() / result.size) * 100) >= 60
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     * Plus we close RealmDatabase since the owner of this thread are the fragments using
     * this ViewModel, when the last fragment is destroy then we close the realm thread.
     */
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
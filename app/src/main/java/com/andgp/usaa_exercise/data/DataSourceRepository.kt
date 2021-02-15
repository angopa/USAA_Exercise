package com.andgp.usaa_exercise.data

import android.app.Application
import com.andgp.usaa_exercise.data.DataStatus.ErrorResponse
import com.andgp.usaa_exercise.data.DataStatus.Success
import com.andgp.usaa_exercise.data.local.Challenge
import com.andgp.usaa_exercise.data.local.USAADatabase
import com.andgp.usaa_exercise.data.remote.IUSAAApi
import com.andgp.usaa_exercise.data.remote.USAAApi
import timber.log.Timber
import kotlin.random.Random

class DataSourceRepository(
    private val application: Application
) {
    private val localDataSource: USAADatabase =
        USAADatabase.getInstance(application.applicationContext)
    private val remoteDataSource: IUSAAApi = USAAApi(application.applicationContext)

    private val challengeDao = localDataSource.challengeDao()

    /**
     * TryObtain local data, if empty, reach server for data and feed local DB.
     * We assume that remote data !isEmpty()
     *
     * @return
     */
    suspend fun tryObtainData(dataStatus: (DataStatus) -> Unit) {
        val result = challengeDao.getData()
        if (result.isEmpty()) {
            val remoteData = remoteDataSource.obtainQuestions()
            if (remoteData == null || remoteData.isEmpty()) {
                dataStatus(ErrorResponse("Error: No data found!"))
            } else {
                val tempList = arrayListOf<Challenge>()
                remoteData.forEach { result ->
                    val challenge = Challenge(
                        question = result.question,
                        answer = result.answer,
                        correct = (Random.nextInt(1, 10) > 5)
                    )
                    tempList.add(challenge)
                }
                challengeDao.insertAll(tempList)
                dataStatus(Success(challengeDao.getData()))
            }
        } else {
            dataStatus(Success(result))
        }
    }

    suspend fun nukeTable() {
        Timber.d("Nuke Table")
        challengeDao.nukeTable()
    }

    companion object {
        @Volatile
        private var INSTANCE: DataSourceRepository? = null

        fun getInstance(
            application: Application
        ): DataSourceRepository {
            return INSTANCE ?: synchronized(this) {
                DataSourceRepository(application)
            }.also { INSTANCE = it }
        }
    }
}

sealed class DataStatus {
    data class Success(val result: List<Challenge>) : DataStatus()
    data class ErrorResponse(val message: String) : DataStatus()
}

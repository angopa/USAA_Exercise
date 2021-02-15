package com.andgp.usaa_exercise.data.remote

import com.andgp.usaa_exercise.data.remote.model.ChallengeResult

interface IUSAAApi {
    suspend fun obtainQuestions(): List<ChallengeResult>?
}

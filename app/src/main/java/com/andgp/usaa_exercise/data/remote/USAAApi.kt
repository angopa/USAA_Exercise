package com.andgp.usaa_exercise.data.remote

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.andgp.usaa_exercise.data.remote.model.ChallengeResult
import com.andgp.usaa_exercise.data.remote.model.Response
import com.google.gson.Gson
import timber.log.Timber
import java.io.IOException

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 *
 *  Dummy class to provided a list of Challenge questions.
 */
class USAAApi(private val context: Context) : IUSAAApi {
    // Question should come from a server, since there is no point of implementing it I just read them
    // from a local file. (questions.txt)
    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun obtainQuestions(): List<ChallengeResult>? =
        try {
            val string = context.assets.open("questions.txt").bufferedReader().use {
                it.readText()
            }
            val response: Response = Gson().fromJson(string, Response::class.java)
            if (response.result.isEmpty()) null else response.result
        } catch (ioException: IOException) {
            Timber.e(ioException)
            null
        }
}
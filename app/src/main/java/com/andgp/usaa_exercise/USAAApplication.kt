package com.andgp.usaa_exercise

import android.app.Application
import com.andgp.usaa_excercise.BuildConfig
import com.andgp.usaa_exercise.data.local.USAADatabase
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
class USAAApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRoomDB()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initRoomDB() {
        USAADatabase.getInstance(this)
    }
}
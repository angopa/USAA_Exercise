package com.andgp.usaa_excercise.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.andgp.usaa_exercise.data.local.Challenge
import com.andgp.usaa_exercise.ui.main.MainViewModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Andres Gonzalez on 02/14/2021.
 * Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE
)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun isCollectionPass_invalidCollection50Percent_Error() {
        val result = mainViewModel.isCollectionPassed(getDummyData_50PercentValid())
        assertThat(result, `is`(false))
    }

    @Test
    fun isCollectionPass_validCollection60Percent_Success() {
        val result = mainViewModel.isCollectionPassed(getDummyData_60PercentValid())
        assertThat(result, `is`(true))
    }

    @Test
    fun isCollectionPass_validCollection100Percent_Success() {
        val result = mainViewModel.isCollectionPassed(getDummyData_100PercentValid())
        assertThat(result, `is`(true))
    }

    @Test
    fun isCollectionPass_emptyCollection_Error() {
        val result = mainViewModel.isCollectionPassed(emptyList())
        assertThat(result, `is`(false))
    }

    @Test
    fun isCollectionPass_noValues_Error() {
        val result = mainViewModel.isCollectionPassed(noValuesList())
        assertThat(result, `is`(false))
    }

    private fun getDummyData_50PercentValid(): List<Challenge> {
        return arrayListOf(
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false)
        )
    }

    private fun getDummyData_60PercentValid(): List<Challenge> {
        return arrayListOf(
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false),
            Challenge(question = "question", answer = "answer", correct = false)
        )
    }

    private fun getDummyData_100PercentValid(): List<Challenge> {
        return arrayListOf(
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true),
            Challenge(question = "question", answer = "answer", correct = true)
        )
    }

    private fun noValuesList(): List<Challenge> {
        return arrayListOf(
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge(),
            Challenge()
        )
    }
}
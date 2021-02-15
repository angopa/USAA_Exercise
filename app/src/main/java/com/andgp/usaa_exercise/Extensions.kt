package com.andgp.usaa_exercise

import com.andgp.usaa_exercise.data.local.Challenge
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
fun List<Challenge>.isCollectionPassed(): Boolean {
    var correctAnswers = 0
    this.forEach { challenge ->
        Timber.d(challenge.toString())
        if (challenge.correct) correctAnswers++
    }
    Timber.d("Correct Answers: $correctAnswers, total questions: ${this.size}, Percentage: ${(correctAnswers.toDouble() / this.size) * 100}")
    return ((correctAnswers.toDouble() / this.size) * 100) >= 60
}
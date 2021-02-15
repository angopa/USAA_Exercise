package com.andgp.usaa_exercise.data.remote.model

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
data class ChallengeResult(
    val question: String,
    val answer: String
) {
    override fun toString(): String {
        return "Question(question='$question', answer='$answer')"
    }
}
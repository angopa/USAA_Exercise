package com.andgp.usaa_exercise.data.local

import android.content.Context
import androidx.room.*

private const val DB_NAME: String = "usaa_db"

/**
 *  Created by Andres Gonzalez on 02/14/2021.
 *  Copyright (c) 2021 City Electric Supply. All rights reserved.
 */
@Database(entities = [Challenge::class], exportSchema = false, version = 1)
abstract class USAADatabase : RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao

    companion object {
        @Volatile
        private var INSTANCE: USAADatabase? = null

        fun getInstance(context: Context): USAADatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    USAADatabase::class.java,
                    DB_NAME
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenge")
    suspend fun getData(): List<Challenge>

    @Insert
    suspend fun insertAll(data: List<Challenge>)

    @Query("DELETE FROM challenge")
    suspend fun nukeTable()
}

@Entity(tableName = "challenge")
data class Challenge(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int = 0,
    @ColumnInfo(name = "question") val question: String = "",
    @ColumnInfo(name = "answer") val answer: String = "",
    @ColumnInfo(name = "correct") val correct: Boolean = false
) {
    override fun toString(): String {
        return "Challenge(Question='$question', Answer='$answer', Correct=$correct)"
    }
}
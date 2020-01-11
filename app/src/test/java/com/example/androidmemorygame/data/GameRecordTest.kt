package com.example.androidmemorygame.data

import android.content.ContentValues
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameRecordTest{
    val points = 10
    val time = 450L
    val speed = 200.0
    val gridSize = 10
    lateinit var gameSettings: GameSettings

    lateinit var gameRecord:GameRecord
    lateinit var contentValues: ContentValues

    @Before
    fun initGameRecord(){
        gameSettings = GameSettings(speed, gridSize)
        gameRecord = GameRecord(points, time, gameSettings)
        contentValues = gameRecord.getContentValues()
    }

    @Test
    fun gameRecord_contentValuesNotNullTest(){
        assertNotNull(gameRecord)
        assertNotEquals(0, contentValues.size())
    }

    @Test
    fun gameRecord_contentValuesTest(){
        assertEquals(speed, contentValues.getAsFloat(GameRecord.GameRecordEntry.SPEED))
        assertEquals(points, contentValues[GameRecord.GameRecordEntry.POINTS])
        assertEquals(time, contentValues[GameRecord.GameRecordEntry.TIME])
        assertEquals(gridSize, contentValues[GameRecord.GameRecordEntry.PAIRS])
    }


}
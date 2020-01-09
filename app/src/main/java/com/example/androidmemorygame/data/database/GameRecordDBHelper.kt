package com.example.androidmemorygame.data.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.androidmemorygame.data.GameRecord
import com.example.androidmemorygame.data.GameRecord.CREATOR.GAME_RECORD_SQL_SELECT_ALL
import com.example.androidmemorygame.data.GameRecord.GameRecordEntry.Companion.PAIRS
import com.example.androidmemorygame.data.GameRecord.GameRecordEntry.Companion.SPEED
import com.example.androidmemorygame.data.GameRecord.GameRecordEntry.Companion.TABLE_NAME
import com.example.androidmemorygame.data.GameRecord.GameRecordEntry.Companion.TIME
import com.example.androidmemorygame.data.GameSettings


fun insertGameRecord(gameRecord: GameRecord, db: SQLiteDatabase) {
    val cv = gameRecord.getContentValues()
    db.insert(TABLE_NAME, null, cv)
}

fun getAllGameRecordsGivenSettings(gameSettings: GameSettings, db: SQLiteDatabase): Cursor {
    val selectQuery =
        GAME_RECORD_SQL_SELECT_ALL + " WHERE " + PAIRS + "='" + gameSettings.gridSize + "' AND " + SPEED + "='" + gameSettings.speed + "' ORDER BY " + TIME + " ASC"
    return db.rawQuery(selectQuery, null)
}
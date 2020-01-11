package com.example.androidmemorygame.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidmemorygame.data.GameRecord
import com.example.androidmemorygame.data.GameRecord.CREATOR.GAME_RECORD_SQL_CREATE_ENTRIES
import com.example.androidmemorygame.data.GameSettings


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MEMORY_GAME"
    }


    private lateinit var db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(GAME_RECORD_SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun insertGameRecord(gameRecord: GameRecord){
        db = this.writableDatabase
        insertGameRecord(gameRecord, db)
    }

    fun getAllGameRecordsGivenSettings(gameSettings: GameSettings):Cursor{
        db = this.readableDatabase
        return getAllGameRecordsGivenSettings(gameSettings, db)
    }
}
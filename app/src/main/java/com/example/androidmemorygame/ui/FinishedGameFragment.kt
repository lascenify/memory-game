package com.example.androidmemorygame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmemorygame.R
import com.example.androidmemorygame.adapters.StatisticsRecordAdapter
import com.example.androidmemorygame.data.GameRecord
import com.example.androidmemorygame.data.database.DBHelper

class FinishedGameFragment : Fragment(){
    private lateinit var lastScoreTV: TextView
    private lateinit var lastTimeTV:TextView
    private lateinit var scoresRecyclerView:RecyclerView
    private lateinit var gridSizeTV:TextView
    private lateinit var speedTV:TextView
    private lateinit var restartButton: Button

    private lateinit var gameRecord: GameRecord

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_finished_game, container, false)
        lastScoreTV = rootView.findViewById(R.id.statistics_score)
        lastTimeTV = rootView.findViewById(R.id.statistics_time)
        scoresRecyclerView = rootView.findViewById(R.id.statistics_recyclerview)
        scoresRecyclerView.layoutManager = LinearLayoutManager(context)
        gridSizeTV = rootView.findViewById(R.id.statistics_category)
        speedTV = rootView.findViewById(R.id.statistics_speed)
        restartButton = rootView.findViewById(R.id.restart_button)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbHelper = DBHelper(context!!)

        val bundle = arguments
        if (bundle != null){
            bindViews(bundle, dbHelper)
            insertRecordOnDatabase(dbHelper)
        }

        val gameRecords = getGameRecords(dbHelper)
        setupAdapter(gameRecords)
        setListeners()
    }

    private fun setupAdapter(gameRecords: ArrayList<GameRecord>) {
        val adapter = StatisticsRecordAdapter(context!!, gameRecords)
        scoresRecyclerView.adapter = adapter
    }

    private fun getGameRecords(dbHelper: DBHelper): ArrayList<GameRecord> {
        val cursor = dbHelper.getAllGameRecordsGivenSettings(gameRecord.gameSettings)
        val gameRecords = GameRecord.fillGameRecordsFromCursor(cursor)
        return gameRecords
    }

    private fun setListeners() {
        restartButton.setOnClickListener {
            findNavController().navigate(R.id.gameSettings)
        }
    }

    private fun bindViews(bundle: Bundle, dbHelper: DBHelper) {
        gameRecord = bundle.getParcelable(getString(R.string.bundle_game_record))!!
        lastScoreTV.text = gameRecord.points.toString()
        gridSizeTV.text = gameRecord.gameSettings.gridSize.toString() + " (" +SettingsFragment.gridSizes.inverse()[gameRecord.gameSettings.gridSize] + " grid)"
        speedTV.text = SettingsFragment.speedValues.inverse()[gameRecord.gameSettings.speed]
        val millis = gameRecord.time
        var seconds = (millis / 1000)
        val minutes = seconds / 60
        seconds %= 60
        lastTimeTV.text = String.format("%d:%02d", minutes, seconds)
    }

    private fun insertRecordOnDatabase(dbHelper: DBHelper) {
        dbHelper.insertGameRecord(gameRecord)
    }
}
package com.example.androidmemorygame.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmemorygame.R
import com.example.androidmemorygame.data.GameRecord
import kotlinx.android.synthetic.main.fragment_game.*
import org.w3c.dom.Text


class StatisticsRecordAdapter internal constructor(context: Context, private var gameRecords: List<GameRecord>): RecyclerView.Adapter<StatisticsRecordAdapter.ViewHolder>() {

    private val inflater :LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.statistics_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = gameRecords.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thisGameRecord = gameRecords[position]
        holder.points.text = thisGameRecord.points.toString()
        val millis = thisGameRecord.time
        var seconds = (millis / 1000)
        val minutes = seconds / 60
        seconds %= 60
        holder.time.text = String.format("%d:%02d", minutes, seconds)
        holder.position.text = (position + 1).toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var points:TextView = itemView.findViewById(R.id.stats_points)
        var time:TextView = itemView.findViewById(R.id.stats_time)
        var position:TextView = itemView.findViewById(R.id.stats_position)
    }

    internal fun setStats(stats: List<GameRecord>){
        this.gameRecords = stats
        notifyDataSetChanged()
    }
}
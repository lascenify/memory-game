package com.example.androidmemorygame.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidmemorygame.R
import com.example.androidmemorygame.adapters.MemoryCardAdapter
import com.example.androidmemorygame.data.GameSettings
import com.example.androidmemorygame.data.MemoryCard
import com.example.androidmemorygame.logic.MemoryGameLogic
import kotlinx.android.synthetic.main.fragment_game.*

class MemoryGameFragment() : Fragment(){

    private lateinit var shuffleButton: Button
    lateinit var gameSettings: GameSettings
    lateinit var mAdapter: MemoryCardAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_game, container, false)
        shuffleButton = rootView.findViewById(R.id.shuffle_button)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var memoryGameLogic:MemoryGameLogic? = null
        var memoryCardList = arrayListOf<MemoryCard>()
        val bundle = arguments
        if (bundle != null){
            memoryCardList = bundle.getParcelableArrayList<MemoryCard>(getString(R.string.bundle_card_array))!!
            gameSettings = bundle.getParcelable(getString(R.string.bundle_settings))!!
            memoryGameLogic = MemoryGameLogic(memoryCardList, gameSettings, this)
        }
        mAdapter = MemoryCardAdapter(memoryCardList, context, memoryGameLogic!!)
        grid_main_cards.adapter = mAdapter

        shuffleButton.setOnClickListener {
            memoryGameLogic.shuffleCards()
        }

        memoryGameLogic.startGame()
    }

    fun setGameFinished(bundle: Bundle){
        findNavController().navigate(R.id.finishedGameFragment, bundle)
    }

    fun setScreenNotTouchable(){
        val handler = Handler()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
        handler.postDelayed(Runnable {
            mAdapter.notifyDataSetChanged()
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }, gameSettings.speed.toLong())
    }

    fun updatePointsView(nPoints: Int){
        show_points.text = nPoints.toString()
    }

    fun updateTimeView(minutes:Long, seconds:Long){
        show_chronometer.text = String.format("%d:%02d", minutes, seconds)
    }

}
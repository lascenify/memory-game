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
import com.example.androidmemorygame.data.GameRecord
import com.example.androidmemorygame.data.GameSettings
import com.example.androidmemorygame.data.MemoryCard
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.collections.ArrayList

class MemoryGameFragment() : Fragment(){

    private lateinit var gameSettings: GameSettings
    private var timerIsStopped = false
    private var memoryCardList = arrayListOf<MemoryCard>()
    private var cardsPositions:ArrayList<Int> = arrayListOf()

    private lateinit var mAdapter:MemoryCardAdapter

    private var nClicks = 0
    private var nPoints = 0
    private var millis = 0L

    private var firstMemoryCard: MemoryCard? = null
    private var secondMemoryCard: MemoryCard? = null

    private lateinit var shuffleButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_game, container, false)
        shuffleButton = rootView.findViewById(R.id.shuffle_button)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null){
            memoryCardList = bundle.getParcelableArrayList<MemoryCard>(getString(R.string.bundle_card_array))!!
            gameSettings = bundle.getParcelable<GameSettings>(getString(R.string.bundle_settings))!!
        }

        mAdapter = MemoryCardAdapter(memoryCardList, context, this)
        grid_main_cards.adapter = mAdapter

        shuffleButton.setOnClickListener {
            shuffleCards()
        }
        startTimer()
    }

    private fun shuffleCards(){
        memoryCardList.shuffle()
        mAdapter.listCards = memoryCardList
        mAdapter.notifyDataSetChanged()
    }

    private fun startTimer() {
        val handler = Handler()
        val start = System.currentTimeMillis()
        val timerRunnable = object : Runnable {
            override fun run() {
                if (!timerIsStopped) {
                    millis = System.currentTimeMillis() - start
                    var seconds = (millis / 1000)
                    val minutes = seconds / 60
                    seconds %= 60
                    show_chronometer.text = String.format("%d:%02d", minutes, seconds)
                    handler.postDelayed(this, 1000)
                }
            }
        }
        timerRunnable.run()
    }

    fun handleClick(position: Int){
        val memoryCard = memoryCardList[position]
        cardsPositions.add(position)
        nClicks ++
        if (nClicks == 1) {
            handleFirstClick(memoryCard)
        }
        else if (nClicks == 2){
            handleSecondClick(position, memoryCard)
        }
        show_points.text = nPoints.toString()
    }

    private fun handleSecondClick(position: Int, memoryCard: MemoryCard) {
        // we check if we click on the first card
        if (position == cardsPositions[0]) {
            nClicks = 0
            memoryCard.isVisible = false
            mAdapter.notifyDataSetChanged()
            cardsPositions.clear()
        } else if (!memoryCard.isVisible) { // we also check if we click on a card that is already visible
            secondMemoryCard = memoryCard
            secondMemoryCard?.isVisible = true
            checkIfCardsMatch()
            cardsPositions.clear()
        } else {
            nClicks--
            cardsPositions.removeAt(cardsPositions.size - 1)
        }
    }


    private fun handleFirstClick(memoryCard: MemoryCard) {
        // We check if it is already visible (already paired)
        if (memoryCard.isVisible) {
            nClicks--
            cardsPositions.remove(0)
        } else {
            firstMemoryCard = memoryCard
            memoryCard.isVisible = true
        }
    }

    private fun checkIfCardsMatch(){
        if (firstMemoryCard?.title.equals(secondMemoryCard?.title)){
            firstMemoryCard = null
            secondMemoryCard = null
            mAdapter.notifyDataSetChanged()
            nPoints ++
            if (nPoints == gameSettings.gridSize){
                setGameFinished()
            }
        }
        else {
            secondMemoryCard?.isVisible = true
            resetCards()
        }
        nClicks = 0
    }

    private fun resetCards(){
        firstMemoryCard?.isVisible = false
        secondMemoryCard?.isVisible = false
        firstMemoryCard = null
        secondMemoryCard = null

        val handler = Handler()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        handler.postDelayed(Runnable {
            mAdapter.notifyDataSetChanged()
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }, gameSettings.speed.toLong())
    }

    private fun setGameFinished(){
        timerIsStopped = true
        val bundle = Bundle()
        val gameRecord = GameRecord(nPoints, millis, gameSettings)
        bundle.putParcelable(getString(R.string.bundle_game_record), gameRecord)
        findNavController().navigate(R.id.finishedGameFragment, bundle)
    }

}
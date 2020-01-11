package com.example.androidmemorygame.logic

import android.os.Bundle
import android.os.Handler
import com.example.androidmemorygame.R
import com.example.androidmemorygame.adapters.MemoryCardAdapter
import com.example.androidmemorygame.data.GameRecord
import com.example.androidmemorygame.data.GameSettings
import com.example.androidmemorygame.data.MemoryCard
import com.example.androidmemorygame.ui.MemoryGameFragment


class MemoryGameLogic (val memoryCardList:ArrayList<MemoryCard>, private val gameSettings: GameSettings, private val memoryGameView: MemoryGameFragment){

    private var mAdapter : MemoryCardAdapter? = null
    private var timerIsStopped = false
    private var millis = 0L
    var cardsPositions:ArrayList<Int> = arrayListOf()
    private var nClicks = 0
    private var nPoints = 0

    private var firstMemoryCard: MemoryCard? = null
    private var secondMemoryCard: MemoryCard? = null

    fun startGame(){
        mAdapter = memoryGameView.mAdapter
        startTimer()
    }

    fun handleClick(position: Int) {
        val memoryCard = memoryCardList[position]
        cardsPositions.add(position)
        nClicks++
        if (nClicks == 1) {
            handleFirstClick(memoryCard)
        } else if (nClicks == 2) {
            handleSecondClick(position, memoryCard)
        }
        memoryGameView.updatePointsView(nPoints)
    }

    fun handleSecondClick(position: Int, memoryCard: MemoryCard) {
        // we check if we click on the first card
        if (position == cardsPositions[0]) {
            nClicks = 0
            memoryCard.isVisible = false
            mAdapter?.notifyDataSetChanged()
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


    fun handleFirstClick(memoryCard: MemoryCard) {
        // We check if it is already visible (already paired)
        if (memoryCard.isVisible) {
            nClicks--
            cardsPositions.remove(0)
        } else {
            firstMemoryCard = memoryCard
            memoryCard.isVisible = true
        }
    }

    private fun checkIfCardsMatch() {
        if (firstMemoryCard?.title.equals(secondMemoryCard?.title)) {
            firstMemoryCard = null
            secondMemoryCard = null
            mAdapter?.notifyDataSetChanged()
            nPoints++
            if (nPoints == gameSettings.gridSize) {
                setGameFinished()
            }
        } else {
            secondMemoryCard?.isVisible = true
            resetCards()
        }
        nClicks = 0
    }

    private fun resetCards() {
        firstMemoryCard?.isVisible = false
        secondMemoryCard?.isVisible = false
        firstMemoryCard = null
        secondMemoryCard = null
        memoryGameView.setScreenNotTouchable()
    }

    private fun setGameFinished() {
        timerIsStopped = true
        val bundle = Bundle()
        val gameRecord = GameRecord(nPoints, millis, gameSettings)
        bundle.putParcelable(memoryGameView.context?.getString(R.string.bundle_game_record), gameRecord)
        memoryGameView.setGameFinished(bundle)
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
                    memoryGameView.updateTimeView(minutes, seconds)

                    handler.postDelayed(this, 1000)
                }
            }
        }
        timerRunnable.run()
    }

    fun shuffleCards() {
        memoryCardList.shuffle()
        mAdapter?.listCards = memoryCardList
        mAdapter?.notifyDataSetChanged()
    }
}
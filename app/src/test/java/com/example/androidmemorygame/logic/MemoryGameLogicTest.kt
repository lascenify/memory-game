package com.example.androidmemorygame.logic

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.androidmemorygame.data.GameSettings
import com.example.androidmemorygame.data.MemoryCard
import com.example.androidmemorygame.ui.MemoryGameFragment
import com.example.androidmemorygame.util.JSONReader
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MemoryGameLogicTest{
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()
    private var samePairOfCards = arrayListOf<MemoryCard>()
    private lateinit var differentCard:MemoryCard
    private var secondClickPosition = 0

    @Mock
    lateinit var memoryGameLogic: MemoryGameLogic

    @Before
    fun initGameVariables(){
        val memoryGameFragment = MemoryGameFragment()
        val jsonReader = JSONReader(context, 10)
        val gameSettings = GameSettings(200.0, 10)
        memoryGameLogic = MemoryGameLogic(jsonReader.memoryCardList, gameSettings, memoryGameFragment)
        getSamePairOfCards()
    }

    @Test
    fun game_firstClickOnInvisibleCard(){
        memoryGameLogic.handleFirstClick(samePairOfCards[0])
        val memoryCardClicked = memoryGameLogic.memoryCardList[0]
        assertTrue(memoryCardClicked.isVisible)
    }

    fun game_firstClickOnPairedCard(){
        memoryGameLogic.handleFirstClick(samePairOfCards[0]) //first click
        memoryGameLogic.handleSecondClick(secondClickPosition, samePairOfCards[1]) // second click

        memoryGameLogic.handleFirstClick(samePairOfCards[0]) // first click again on the paired card
        val memoryCardClicked = memoryGameLogic.memoryCardList[0]
        assertTrue(memoryCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnInvisibleCard(){
        memoryGameLogic.handleFirstClick(samePairOfCards[0]) //first click
        memoryGameLogic.cardsPositions.add(0)
        memoryGameLogic.handleSecondClick(secondClickPosition, samePairOfCards[1]) // second click
        val memoryCardClicked = memoryGameLogic.memoryCardList[secondClickPosition]
        assertTrue(memoryCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnFirstClickedCard(){
        memoryGameLogic.handleFirstClick(samePairOfCards[0]) //first click
        memoryGameLogic.cardsPositions.add(0)
        memoryGameLogic.handleSecondClick(0, samePairOfCards[0]) // second click
        val memoryCardClicked = memoryGameLogic.memoryCardList[0]
        assertFalse(memoryCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnPairedCard(){
        memoryGameLogic.handleFirstClick(samePairOfCards[0]) //first click
        memoryGameLogic.cardsPositions.add(0)
        memoryGameLogic.handleSecondClick(secondClickPosition, samePairOfCards[1]) // second click

        val thirdMemoryCard = if (secondClickPosition == 1)
            memoryGameLogic.memoryCardList[secondClickPosition+1]
            else
                memoryGameLogic.memoryCardList[secondClickPosition-1]

        memoryGameLogic.handleFirstClick(thirdMemoryCard) // first click on a different card
        memoryGameLogic.cardsPositions.add(secondClickPosition + 1)
        memoryGameLogic.handleSecondClick(secondClickPosition, samePairOfCards[1])

        val memoryCardClicked = memoryGameLogic.memoryCardList[secondClickPosition]
        assertTrue(memoryCardClicked.isVisible)
    }

    private fun getSamePairOfCards(){
        samePairOfCards.add(memoryGameLogic.memoryCardList[0])

        val sameCardTitle = samePairOfCards[0].title
        var secondCard:MemoryCard
        secondClickPosition = 0
        do{
            secondClickPosition ++
            secondCard = memoryGameLogic.memoryCardList[secondClickPosition]

        } while (!secondCard.title.equals(sameCardTitle))

        samePairOfCards.add(secondCard)
        differentCard = memoryGameLogic.memoryCardList.first { it.title.equals(samePairOfCards[0].title) }
    }

    @After
    fun clearVariables(){
        samePairOfCards = arrayListOf()
    }
}
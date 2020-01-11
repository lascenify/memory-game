package com.example.androidmemorygame.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JSONReaderTest{
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()
    private val gridSize = 10
    @Mock
    lateinit var jsonReader: JSONReader

    @Before
    fun initJsonReader(){
        jsonReader = JSONReader(context, gridSize)
    }

    @Test
    fun jsonReader_assertCardListNotNull(){
        assertNotNull(jsonReader.memoryCardList)
    }

    @Test
    fun jsonReader_assertCardListLength(){
        assertEquals(gridSize*2, jsonReader.memoryCardList.size)
    }
}
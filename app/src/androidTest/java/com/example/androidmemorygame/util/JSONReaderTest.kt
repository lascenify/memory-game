package com.example.androidmemorygame.util

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JSONReaderTest{
    @Test
    fun getMemoryCardList(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val jsonReader = JSONReader(context, 10)
        jsonReader.readJSONFile()
        assertNotNull(jsonReader.memoryCardList)
        assertEquals(jsonReader.memoryCardList.size, 20)
    }
}
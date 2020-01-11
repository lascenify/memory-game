package com.example.androidmemorygame.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.androidmemorygame.R
import com.example.androidmemorygame.data.MemoryCard
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class JSONReader (val context: Context, val gridSize:Int) {
    val FILE_NAME = "products.json"
    private val PRODUCTS_ARRAY = "products"
    private val PRODUCT_ID = "id"
    private val PRODUCT_TITLE = "title"
    private val PRODUCT_IMAGE = "image"
    private val PRODUCT_IMAGE_SRC = "src"
    var memoryCardList = arrayListOf<MemoryCard>()

    init {
        readJSONFile()
    }

    private fun readJSONFile(){
        try {
            val inputStream: InputStream = context.assets.open(FILE_NAME)
            val inputString = inputStream.bufferedReader().use{it.readText()}
            val jsonObject = JSONObject(inputString)
            val jsonArray: JSONArray = jsonObject.getJSONArray(PRODUCTS_ARRAY)
            for (i in 0 until gridSize){
                val memoryCardObject = jsonArray.getJSONObject(i)
                val id = memoryCardObject.getLong(PRODUCT_ID)
                val title = memoryCardObject.getString(PRODUCT_TITLE)
                val imageArray = memoryCardObject.getJSONObject(PRODUCT_IMAGE)
                val imageSrc = imageArray.getString(PRODUCT_IMAGE_SRC)
                val memoryCard = MemoryCard(id, title, imageSrc)
                memoryCardList.add(memoryCard)
                memoryCardList.add(memoryCard.copy())
            }
            memoryCardList.shuffle()
        } catch (e:Exception){
            Log.d("JSON Exception", e.toString())

        }

    }

}
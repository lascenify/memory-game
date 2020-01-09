package com.example.androidmemorygame.util

import android.content.Context
import android.util.Log
import com.example.androidmemorygame.data.MemoryCard
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class JSONReader () {
    private val FILE_NAME = "products.json"
    private val PRODUCTS_ARRAY = "products"
    private val PRODUCT_ID = "id"
    private val PRODUCT_TITLE = "title"
    private val PRODUCT_IMAGE = "image"
    private val PRODUCT_IMAGE_SRC = "src"


    lateinit var context:Context
    var gridSize:Int = 0
    constructor(context: Context, size:Int) : this() {
        this.context = context
        this.gridSize = size
        readJSONFile()
    }
    var memoryCardList = arrayListOf<MemoryCard>()

    fun readJSONFile(){
        try {
            val inputStream: InputStream = context.assets.open(FILE_NAME)
            val inputString = inputStream.bufferedReader().use{it.readText()}
            val jsonObject: JSONObject = JSONObject(inputString)
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
        } catch (e:Exception){
            Log.d("JSON Exception", e.toString())
        }
        memoryCardList.shuffle()
    }
}
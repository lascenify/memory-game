package com.example.androidmemorygame.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.androidmemorygame.ui.MemoryGameFragment
import com.example.androidmemorygame.R
import com.example.androidmemorygame.data.MemoryCard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.memory_card_hidden_item.view.*


class MemoryCardAdapter(var listCards: ArrayList<MemoryCard>, var context: Context?, var memoryGame: MemoryGameFragment) :
    BaseAdapter() {
    var listCardViews = ArrayList<ImageView>()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val thisCard = listCards[position]

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cardview = inflater.inflate(R.layout.memory_card_hidden_item, null)
        listCardViews.add(cardview.card_item)
        cardview.setOnClickListener{
            cardview.card_item.background = null
            //Picasso.get().load(thisCard.image).into(cardview.card_item)
            Picasso.get()
                .load(thisCard.image)
                .placeholder(R.drawable.ic_interrogation)
                .into(cardview.card_item)
            memoryGame.handleClick(position)
        }

        if (thisCard.isVisible){
            val url = thisCard.image

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_interrogation)
                .into(cardview.card_item)

        }
        else{
            Picasso.get().load(R.drawable.ic_interrogation)
                .placeholder(R.drawable.ic_interrogation)
                .into(cardview.card_item);
        }

        return cardview
    }

    override fun getItem(position: Int): Any {
        return listCards[position]
    }

    override fun getItemId(position: Int): Long {
        return listCards[position].id
    }

    override fun getCount(): Int {
        return listCards.size
    }



}
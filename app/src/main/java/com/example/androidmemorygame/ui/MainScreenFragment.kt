package com.example.androidmemorygame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidmemorygame.R

class MainScreenFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_main_screen, container, false)
        rootView.findViewById<Button>(R.id.init_button).setOnClickListener{
            it.findNavController().navigate(R.id.gameSettings)
        }
        return rootView
    }

}
package com.example.androidmemorygame.ui

import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.androidmemorygame.R
import com.example.androidmemorygame.data.GameSettings
import com.example.androidmemorygame.util.JSONReader
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

class SettingsFragment : Fragment(){
    lateinit var spinner: Spinner
    lateinit var radioGroup: RadioGroup
    lateinit var startGameButton:Button

    companion object{
        var gridSizes: BiMap<String, Int> = HashBiMap.create()
        const val SMALL_GRID = "small"
        const val MID_GRID = "middle"
        const val BIG_GRID = "big"

        var speedValues : BiMap<String, Double> = HashBiMap.create()
        const val LOW_SPEED = "low"
        const val MID_SPEED = "middle"
        const val FAST_SPEED = "fast"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        spinner = rootView.findViewById(R.id.grid_size_spinner)
        radioGroup = rootView.findViewById(R.id.difficulty_radiogroup)
        startGameButton = rootView.findViewById(R.id.start_game_button)

        initializeHashMaps()
        return rootView
    }

    private fun initializeHashMaps() {
        gridSizes[SMALL_GRID] = 10
        gridSizes[MID_GRID] = 12
        gridSizes[BIG_GRID] = 14

        speedValues[LOW_SPEED] = 2000.0
        speedValues[MID_SPEED] = 1000.0
        speedValues[FAST_SPEED] = 500.0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGameButton.setOnClickListener{
            val selectedItem = spinner.selectedItemId
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            val gridSize = getGridSizeFromSelectedId(selectedItem)!!
            val jsonReader = JSONReader(context!!, gridSize)
            val speed = getSpeedFromSelectedRadioButtonId(checkedRadioButtonId)!!

            val memoryCardList = jsonReader.memoryCardList
            if (memoryCardList.isNullOrEmpty()){
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        findNavController().navigate(R.id.mainScreen)
                    })
                    .setTitle("The file " + jsonReader.FILE_NAME + " could not be read due to an exception")
                alertDialogBuilder.create().show()
            }
            else {
                val bundle = Bundle()
                val settings = GameSettings(speed, gridSize)
                bundle.putParcelable(getString(R.string.bundle_settings), settings)
                bundle.putParcelableArrayList(getString(R.string.bundle_card_array), jsonReader.memoryCardList)

                findNavController().navigate(R.id.memoryGame, bundle)
            }
        }
    }

    private fun getGridSizeFromSelectedId(selectedItem:Long) = when (selectedItem){
        0L -> gridSizes[SMALL_GRID]
        1L -> gridSizes[MID_GRID]
        2L -> gridSizes[BIG_GRID]
        else -> gridSizes[MID_GRID]
    }

    private fun getSpeedFromSelectedRadioButtonId(selectedDifficulty:Int) = when (selectedDifficulty){
        R.id.easy_radiobutton -> speedValues[LOW_SPEED]
        R.id.normal_radiobutton -> speedValues[MID_SPEED]
        R.id.hard_radiobutton -> speedValues[FAST_SPEED]
        else -> speedValues[MID_SPEED]
    }
}
package com.template

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val slots = listOf(
        "\uD83D\uDCB0",
        "\uD83C\uDF52",
        "\uD83C\uDF53",
        "\uD83C\uDF4B",
        "\uD83C\uDF4C",
        "\uD83C\uDF47"
    )

    private lateinit var slot1: TextView
    private lateinit var slot2: TextView
    private lateinit var slot3: TextView
    private lateinit var slot4: TextView
    private lateinit var slot5: TextView
    private lateinit var slot6: TextView
    private lateinit var slot7: TextView
    private lateinit var slot8: TextView
    private lateinit var slot9: TextView

    private lateinit var buttonBet: Button
    private lateinit var buttonSpin: Button
    private lateinit var buttonMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewSlots()

        buttonSpin.setOnClickListener {
            startFirstSlots()
        }


    }

    private fun initViewSlots(){
        with(binding) {
            slot1 = txtSlot1
            slot2 = txtSlot2
            slot3 = txtSlot3
            slot4 = txtSlot4
            slot5 = txtSlot5
            slot6 = txtSlot6
            slot7 = txtSlot7
            slot8 = txtSlot8
            slot9 = txtSlot9
            buttonBet = btnBet
            buttonSpin = btnSpin
            buttonMenu = buttonMenu
        }

    }

    private fun startFirstSlots(){
        slot1.text = slots.random()
        slot2.text = slots.random()
        slot3.text = slots.random()
        slot4.text = slots.random()
        slot5.text = slots.random()
        slot6.text = slots.random()
        slot7.text = slots.random()
        slot8.text = slots.random()
        slot9.text = slots.random()
    }

}
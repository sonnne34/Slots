package com.template

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.template.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val slotsTextImg = listOf(
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
        onClickListeners()


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
            buttonMenu = btnMenu
        }
    }

    private fun onClickListeners(){
        buttonSpin.setOnClickListener {

            val timer = object: CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    startSlotsRandom()
                }
                override fun onFinish() {
                    Toast.makeText(this@GameActivity, "!!!", Toast.LENGTH_LONG).show()
                }
            }
            timer.start()

        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonBet.setOnClickListener {

        }
    }

    private fun startSlotsRandom(){
        slot1.text = slotsTextImg.random()
        slot2.text = slotsTextImg.random()
        slot3.text = slotsTextImg.random()
        slot4.text = slotsTextImg.random()
        slot5.text = slotsTextImg.random()
        slot6.text = slotsTextImg.random()
        slot7.text = slotsTextImg.random()
        slot8.text = slotsTextImg.random()
        slot9.text = slotsTextImg.random()

        slotsSpin()
    }

    private fun slotsSpin() {
        lifecycleScope.launch {
            generateSlots()
        }
    }

    private suspend fun generateSlots(){
        delay(300)
        startSlotsRandom()
    }


}
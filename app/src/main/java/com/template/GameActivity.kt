package com.template

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.template.databinding.ActivityGameBinding
import kotlinx.coroutines.launch

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

    private fun initViewSlots() {

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

    private fun onClickListeners() {
        buttonSpin.setOnClickListener {
            startGameSpin()
        }

        buttonMenu.setOnClickListener {
            goMenu()
        }
        buttonBet.setOnClickListener {

        }
    }

    private fun startGameSpin(){
        lifecycleScope.launch {
            spinLinesSlots(slot1, slot2, slot3, 120)
            spinLinesSlots(slot4, slot5, slot6, 110)
            spinLinesSlots(slot7, slot8, slot9, 100)
        }
    }

    private fun spinLinesSlots(
        tvSlot1: TextView,
        tvSlot2: TextView,
        tvSlot3: TextView,
        count: Long
    ) {

        anim(tvSlot1, count)
        anim(tvSlot2, count)
        anim(tvSlot3, count)

        val timer = object : CountDownTimer(5000, count) {
            override fun onTick(millisUntilFinished: Long) {
                slotsTvImageRandom(tvSlot1, tvSlot2, tvSlot3)
            }

            override fun onFinish() {
                clearAnim(tvSlot1)
                clearAnim(tvSlot2)
                clearAnim(tvSlot3)

                showToast()
            }
        }
        timer.start()
    }

    private fun slotsTvImageRandom(
        tvSlot1: TextView,
        tvSlot2: TextView,
        tvSlot3: TextView
    ) {
        tvSlot1.text = slotsTextImg.random()
        tvSlot2.text = slotsTextImg.random()
        tvSlot3.text = slotsTextImg.random()
    }

    private fun anim(tv: TextView, count: Long) {
        val animation: Animation = AlphaAnimation(0.3f, 1.0f)
        with(animation) {
            duration = count
            startOffset = 50
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        tv.startAnimation(animation)
    }

    private fun clearAnim(tv: TextView) {
        tv.clearAnimation()
    }

    private fun showToast() {
        val toast = Toast.makeText(
            this@GameActivity,
            "Your winnings: N",
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun goMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
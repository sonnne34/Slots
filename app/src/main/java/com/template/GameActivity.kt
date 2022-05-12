package com.template

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.Gravity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.template.MainActivity.Companion.COINS
import com.template.databinding.ActivityGameBinding
import kotlin.system.exitProcess

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
        "\uD83C\uDF47",
        "\uD83C\uDFB0"
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

    private lateinit var txtCoins: TextView
    private lateinit var txtBet: TextView

    private var coins = 1000
    private var bet = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        coins = intent.getIntExtra(COINS, 1000)

        initView()
        onClickListeners()
        tvCoinsDefault()
        tvBetDefault()
    }

    private fun initView() {
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
            txtCoins = txtCoinsGame
            txtBet = txtBetGame
        }
    }

    private fun onClickListeners() {
        buttonSpin.setOnClickListener { goSpin() }
        buttonMenu.setOnClickListener { goMenu() }
        buttonBet.setOnClickListener {
            showDialogBet()
            tvBetDefault()
        }
    }

    private fun goSpin() {
        if (coins >= bet) {
            coins -= bet
            if (coins >= 0) {
                tvCoinsDefault()
                startGameSpin()
            } else {
                showDialogGameOver()
            }
        } else {
            showToast("Not enough coins")
        }
    }

    private fun startGameSpin() {
        spinLinesSlots(slot1, slot2, slot3, 120, false)
        spinLinesSlots(slot4, slot5, slot6, 110, false)
        spinLinesSlots(slot7, slot8, slot9, 100, true)
    }

    private fun spinLinesSlots(
        tvSlot1: TextView,
        tvSlot2: TextView,
        tvSlot3: TextView,
        count: Long,
        lostLine: Boolean
    ) {
        clickableButton(false)

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

                if (lostLine) {
                    val rewardCoins = (-100..bet * 2).random()
                    showToast("Your reward $rewardCoins coins")
                    coins += rewardCoins
                    tvCoinsDefault()
                    clickableButton(true)
                }
            }
        }
        timer.start()
    }

    private fun slotsTvImageRandom(tvSlot1: TextView, tvSlot2: TextView, tvSlot3: TextView) {
        tvSlot1.text = slotsTextImg.random()
        tvSlot2.text = slotsTextImg.random()
        tvSlot3.text = slotsTextImg.random()
    }

    private fun anim(tv: TextView, count: Long) {
        val animation: Animation = AlphaAnimation(0.3f, 1.0f).apply {
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

    private fun clickableButton(status: Boolean) {
        buttonBet.isClickable = status
        buttonSpin.isClickable = status
        buttonMenu.isClickable = status
    }

    private fun tvCoinsDefault() {
        txtCoins.text = String.format(
            application.resources.getString(R.string.your_coins_n),
            coins
        )
    }

    private fun tvBetDefault() {
        txtBet.text = String.format(
            application.resources.getString(R.string.your_bet_n),
            bet
        )
    }

    private fun showDialogBet() {
        AlertDialog.Builder(this).apply {
            setTitle("Bet")
            val input = EditText(this@GameActivity)
            input.hint = "Enter your Bet"
            input.gravity = Gravity.CENTER
            input.inputType = InputType.TYPE_CLASS_NUMBER
            setView(input)
            setPositiveButton(
                "Ok"
            ) { _, _ ->
                bet = input.text.toString().toInt()
                tvBetDefault()
            }
            show()
        }
    }

    private fun showDialogGameOver() {
        AlertDialog.Builder(this).apply {
            setTitle("Game Over")
            setMessage("Your coins have run out")
            setPositiveButton(
                "Ok"
            ) { _, _ ->
                goMenu()
            }
            setCancelable(false)
            show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@GameActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun goMenu() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(COINS, coins)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure you want to quit the App?")
            setPositiveButton("Yes") { _, _ ->
                finishAffinity()
                exitProcess(0)
            }
            setNegativeButton("Oh, no!") { _, _ ->
                Toast.makeText(
                    this@GameActivity, "Thank you",
                    Toast.LENGTH_LONG
                ).show()
            }
            setCancelable(true)
        }.create().show()
    }
}
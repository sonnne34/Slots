package com.template

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var coins = 1000
    private var rewardCoins = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        defaultCoins()

        with(binding) {
            btnPlay.setOnClickListener { checkCoins() }
            btnLottery.setOnClickListener { goLottery() }
            txtCoins.text = String.format(
                application.resources.getString(R.string.your_coins_n),
                coins
            )
        }
    }

    private fun defaultCoins() {
        coins = intent.getIntExtra(COINS, 1000)

        if (coins < 0) {
            coins = 0
        }
    }

    private fun checkCoins() {
        if (coins <= 0) {
            showDialog()
        } else {
            goGame()
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        with(dialog) {
            setTitle("Wow!")
            setMessage("Your reward $rewardCoins coins")
            setPositiveButton(
                "claim"
            ) { _, _ ->
                coins = rewardCoins
            }
            show()
        }
    }

    private fun goGame() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(COINS, coins)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }

    private fun goLottery() {
        val intent = Intent(this, LotteryActivity::class.java)
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
                    this@MainActivity, "Thank you",
                    Toast.LENGTH_LONG
                ).show()
            }
            setCancelable(true)
        }.create().show()
    }

    companion object {
        const val COINS = "COINS"
    }
}
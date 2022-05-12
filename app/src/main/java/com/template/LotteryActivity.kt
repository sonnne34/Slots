package com.template

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.template.MainActivity.Companion.COINS
import com.template.databinding.ActivityLotteryBinding
import kotlin.system.exitProcess

class LotteryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLotteryBinding.inflate(layoutInflater)
    }

    private var rewardCoins = (0..1111).random()
    private var scoreCoins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val coins = intent.getIntExtra(COINS, 1000)
        scoreCoins = coins + rewardCoins

        with(binding) {
            txtYourRewardLottery.isVisible = false
            btnLotteryOne.setOnClickListener { selectBox() }
            btnLotteryTwo.setOnClickListener { selectBox() }
            btnLotteryThree.setOnClickListener { selectBox() }
        }
    }

    private fun selectBox() {
        showRewardDialog()
        binding.txtYourRewardLottery.text = String.format(
            application.resources.getString(R.string.your_coins_n),
            rewardCoins
        )
        binding.txtYourRewardLottery.isVisible = true
    }

    private fun showRewardDialog() {
        val dialog = AlertDialog.Builder(this)
        with(dialog) {
            setTitle("Wow!")
            setMessage("Your reward $rewardCoins coins")
            setPositiveButton(
                "claim"
            ) { _, _ ->
                goMenu()
            }
            show()
        }
    }

    private fun goMenu() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(COINS, scoreCoins)
        startActivity(intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP))
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
                    this@LotteryActivity, "Thank you",
                    Toast.LENGTH_LONG
                ).show()
            }
            setCancelable(true)
        }.create().show()
    }
}
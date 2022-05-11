package com.template

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.template.databinding.ActivityLotteryBinding
import kotlin.random.Random

class LotteryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLotteryBinding.inflate(layoutInflater)
    }

    private var rewardCoins = Random(1111)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {

            txtYourRewardLottery.isVisible = false

            btnLotteryOne.setOnClickListener {
                showDialog()
                txtYourRewardLottery.isVisible = true
            }
            btnLotteryTwo.setOnClickListener {
                showDialog()
                txtYourRewardLottery.isVisible = true
            }
            btnLotteryThree.setOnClickListener {
                showDialog()
                txtYourRewardLottery.isVisible = true
            }
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        with(dialog){
            setTitle("Wow!")
            setMessage("Your reward $rewardCoins coins")
            setPositiveButton(
                "claim") { _, _ ->
                onBackPressed()
            }
            show()
        }
    }
}
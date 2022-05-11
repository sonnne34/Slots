package com.template

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var coins = 1000
    private var rewardCoins = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            checkCoins()
        }

        binding.btnLottery.setOnClickListener {
            goLottery()
        }

        binding.txtCoins.text = String.format(
            application.resources.getString(R.string.your_coins_n),
            coins)
    }

    private fun checkCoins(){
        if (coins == 0) {
            showDialog()
        } else {
            goGame()
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        with(dialog){
            setTitle("Wow!")
            setMessage("Your reward $rewardCoins coins")
            setPositiveButton(
                "claim") { _, _ ->
                coins = rewardCoins
            }
            show()
        }
    }

    private fun goGame(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun goLottery(){
        val intent = Intent(this, LotteryActivity::class.java)
        startActivity(intent)
    }
}
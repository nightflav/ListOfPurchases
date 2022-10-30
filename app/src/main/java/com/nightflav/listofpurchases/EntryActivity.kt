package com.nightflav.listofpurchases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nightflav.listofpurchases.databinding.ActivityEntryBinding

class EntryActivity : AppCompatActivity() {

    lateinit var binding: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var currentScreen = 1
        binding.firstEntryScreen.visibility = View.VISIBLE

        binding.entryScreenButtonNext.setOnClickListener {
            when(currentScreen) {
                0 -> {
                    currentScreen++
                    binding.firstEntryScreen.visibility = View.VISIBLE
                    binding.secondEntryScreen.visibility = View.GONE
                    binding.thirdEntryScreen.visibility = View.GONE
                }
                1 -> {
                    currentScreen++
                    binding.firstEntryScreen.visibility = View.GONE
                    binding.secondEntryScreen.visibility = View.VISIBLE
                    binding.thirdEntryScreen.visibility = View.GONE
                }
                2 -> {
                    currentScreen++
                    binding.firstEntryScreen.visibility = View.GONE
                    binding.secondEntryScreen.visibility = View.GONE
                    binding.thirdEntryScreen.visibility = View.VISIBLE
                }
                else -> {finish()}
            }
        }
    }
}
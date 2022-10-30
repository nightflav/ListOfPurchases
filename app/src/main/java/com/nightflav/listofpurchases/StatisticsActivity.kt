package com.nightflav.listofpurchases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nightflav.listofpurchases.databinding.ActivityStatisticsBinding

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //bottom navigation menu implementation
        binding.navigationBottom.selectedItemId = R.id.statistic;

        // Perform item selected listener
        binding.navigationBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.statistic -> return@setOnItemSelectedListener true
                R.id.list_of_purchases -> {
                    finish()
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
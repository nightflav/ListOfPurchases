package com.nightflav.listofpurchases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.nightflav.listofpurchases.databinding.ActivityStatisticsBinding
import com.nightflav.listofpurchases.model.Item

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //bottom navigation menu implementation
        binding.navigationBottom.selectedItemId = R.id.statistic;
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

        //stats
        binding.tvAmountOfItemsTitle.text = intent.getStringExtra(EXTRA_STATISCTICS_KEY)
    }

    companion object {
        @JvmStatic
        val EXTRA_STATISCTICS_KEY = "Statistics Key"
    }
}
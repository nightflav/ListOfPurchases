package com.nightflav.listofpurchases

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightflav.listofpurchases.databinding.ActivityPurchasesBinding
import com.nightflav.listofpurchases.model.ItemData
import com.nightflav.listofpurchases.model.itemListener

class PurchasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchasesBinding

    private lateinit var adapter: ItemListAdapter

    private val itemsService = ItemData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //first launch activity implementation
        runFirstTimeLaunchActivity()

        //recyclerView
        adapter = ItemListAdapter(object : UserActionListener {
            override fun addOneItem(id: String) {
                itemsService.increaseAmountOfUniqueItem(id)
            }

            override fun removeOneItem(id: String) {
                itemsService.decreaseAmountOfUniqueItem(id)
            }

            override fun deleteItem(id: String) {
                itemsService.deleteItem(id)
            }
        })
        binding.rvItemList.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvItemList.layoutManager = layoutManager

        itemsService.addListener(itemListener)

        // bottom menu navigation

        binding.navigationBottom.selectedItemId = R.id.list_of_purchases;
        binding.navigationBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.list_of_purchases -> return@setOnItemSelectedListener true
                R.id.statistic -> {
                    val intent = Intent(this, StatisticsActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private val itemListener: itemListener = {
        adapter.items = it
    }

    override fun onDestroy() {
        super.onDestroy()
        itemsService.deleteListener(itemListener)
    }

    private fun makeLikeFirstTimeOpenAgain() {
        val sharedPref = getSharedPreferences("hasBeenOpened", Context.MODE_PRIVATE)
        sharedPref.edit().putString(IS_OPENED_KEY, "Yes").apply()
    }

    private fun runFirstTimeLaunchActivity() {
        val sharedPref = getSharedPreferences("hasBeenOpened", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPref.getString(IS_OPENED_KEY, "Yes")

        if (isFirstLaunch == "Yes") {
            startActivity(Intent(this@PurchasesActivity, EntryActivity::class.java))
            val editor = sharedPref.edit()
            editor.putString(IS_OPENED_KEY, "No")
            editor.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.navigationBottom.selectedItemId = R.id.list_of_purchases
    }

    companion object {
        @JvmStatic
        val IS_OPENED_KEY = "Been Opened"
    }
}
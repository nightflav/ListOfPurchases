package com.nightflav.listofpurchases

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightflav.listofpurchases.databinding.ActivityPurchasesBinding
import com.nightflav.listofpurchases.databinding.FragmentAddItemDialogBinding
import com.nightflav.listofpurchases.model.Item
import com.nightflav.listofpurchases.model.ItemData
import com.nightflav.listofpurchases.model.itemListener


class PurchasesActivity : AppCompatActivity() {

    private var currentId = 0

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
                        .putExtra(EXTRA_STATISCTICS_KEY, adapter.items.size.toString())
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        //add button implementation
        binding.fbAdd.setOnClickListener {
            addItem()
        }

        //make start screen again
        binding.button.setOnClickListener { makeLikeFirstTimeOpenAgain() }
    }

    private fun addItem() {
        val dialog = Dialog(this)
        val dialogBinding = FragmentAddItemDialogBinding.inflate(layoutInflater)
        dialogBinding.btnAdd.setOnClickListener {
            val newItem = Item("CategoryType", dialogBinding.etInputTitle.text.toString(),
            dialogBinding.etInputAmount.text.toString().toInt(), currentId.toString())
            currentId++
            itemsService.addItem(newItem)
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.etInputTitle.setOnClickListener {
            dialogBinding.etInputTitle.text.clear()
        }
        dialogBinding.etInputAmount.setOnClickListener {
            dialogBinding.etInputAmount.text.clear()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
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
        val EXTRA_STATISCTICS_KEY = "Statistics Key"
    }
}
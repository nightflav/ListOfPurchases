package com.nightflav.listofpurchases

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        //splash screen implementation
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, PurchasesActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }, 1500)
    }

}
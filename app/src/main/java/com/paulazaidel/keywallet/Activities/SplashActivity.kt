package com.paulazaidel.keywallet.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2000

    private val callNextActivity: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        delayHandler = Handler()

        //Navigate with delay
        delayHandler!!.postDelayed(callNextActivity, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(callNextActivity)
        }

        super.onDestroy()

    }
}

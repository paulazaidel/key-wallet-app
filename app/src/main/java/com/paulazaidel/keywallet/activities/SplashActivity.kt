package com.paulazaidel.keywallet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null

    private val callNextActivity: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        delayHandler = Handler()

        //Navigate with delay
        delayHandler!!.postDelayed(callNextActivity, 2000)
    }

    public override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(callNextActivity)
        }

        super.onDestroy()

    }
}

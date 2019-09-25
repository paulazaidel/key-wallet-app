package com.paulazaidel.mypasswords.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.paulazaidel.mypasswords.DataBase.AppDatabase
import com.paulazaidel.mypasswords.R

import kotlinx.android.synthetic.main.activity_list.*
import com.paulazaidel.mypasswords.Models.ListViewAdapter
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        lv_accounts.setOnItemClickListener { parent, view, position, id ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()

        getAllAccounts()
    }

    private fun getAllAccounts(){
        val accounts = AppDatabase.getAppDatabase(this).accountDao().getAll()
        lv_accounts.adapter = ListViewAdapter(this, accounts)
    }

}

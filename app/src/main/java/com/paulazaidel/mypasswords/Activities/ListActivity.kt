package com.paulazaidel.mypasswords.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.paulazaidel.mypasswords.DataBase.AppDatabase
import com.paulazaidel.mypasswords.Models.Account
import com.paulazaidel.mypasswords.R

import kotlinx.android.synthetic.main.activity_list.*
import com.paulazaidel.mypasswords.Models.ListViewAdapter
import com.paulazaidel.mypasswords.Models.ShowAccountFragment
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : AppCompatActivity() {

    lateinit var adapter : ListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        lv_accounts.setOnItemClickListener { parent, view, position, id ->
            var account = adapter.getItem(position)

            showAccount(account)
        }
    }

    private fun showAccount(account: Account) {
        val fragment = ShowAccountFragment(account)
        fragment.show(supportFragmentManager, "show_account_layout")
    }

    override fun onStart() {
        super.onStart()

        getAllAccounts()
    }

    private fun getAllAccounts(){
        val accounts = AppDatabase.getAppDatabase(this).accountDao().getAll()
        adapter = ListViewAdapter(this, accounts)
        lv_accounts.adapter = adapter
    }

}

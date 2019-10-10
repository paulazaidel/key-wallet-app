package com.paulazaidel.keywallet.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.paulazaidel.keywallet.DataBase.AppDatabase
import com.paulazaidel.keywallet.Models.Account
import com.paulazaidel.keywallet.R

import kotlinx.android.synthetic.main.activity_list.*
import com.paulazaidel.keywallet.Models.ListViewAdapter
import com.paulazaidel.keywallet.Models.ShowAccountFragment
import com.paulazaidel.keywallet.Models.SwipeMenu
import kotlinx.android.synthetic.main.content_list.*


class ListActivity : AppCompatActivity() {

    private lateinit var adapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        lv_accounts.setOnItemClickListener { _, _, position, _ ->
            var account = adapter.getItem(position)
            showAccount(account)
        }

        lv_accounts.setOnMenuItemClickListener { position, _, index ->
            if (index == 0) { // Edit
                var account = adapter.getItem(position)
                val intent = Intent(this, AccountActivity::class.java)
                intent.putExtra("account", account)
                startActivity(intent)


            } else if (index == 1) { // Delete
                var account = adapter.getItem(position)
                showDeleteDialog(account)
            }

            // Close menu
            false
        }
    }

    private fun showDeleteDialog(account: Account) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(account.description)
        alert.setMessage(R.string.app_remove_question)

        alert.setPositiveButton(R.string.app_yes) { dialog, _ ->
            deleteAccoun(account)
            dialog.dismiss()
        }
        alert.setNegativeButton(R.string.app_no) { dialog, _ ->
            dialog.cancel()
            dialog.dismiss()
        }

        alert.create().show()
    }

    private fun deleteAccoun(account: Account){
        AppDatabase.getAppDatabase(this).accountDao().delete(account)
        getAllAccounts()

        Toast.makeText(this, R.string.app_remove_successfully, Toast.LENGTH_SHORT).show()
    }

    private fun showAccount(account: Account) {
        val fragment = ShowAccountFragment(account)
        fragment.show(supportFragmentManager, "show_account_layout")
    }

    override fun onStart() {
        super.onStart()
        getAllAccounts()
    }

    private fun getAllAccounts() {
        val accounts = AppDatabase.getAppDatabase(this).accountDao().getAll()
        adapter = ListViewAdapter(this, accounts)
        lv_accounts.adapter = adapter
        lv_accounts.setMenuCreator(SwipeMenu(this))
    }
}

package com.paulazaidel.mypasswords.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.paulazaidel.mypasswords.DataBase.AppDatabase
import com.paulazaidel.mypasswords.Models.Account
import com.paulazaidel.mypasswords.R

import kotlinx.android.synthetic.main.activity_list.*
import com.paulazaidel.mypasswords.Models.ListViewAdapter
import com.paulazaidel.mypasswords.Models.ShowAccountFragment
import com.paulazaidel.mypasswords.Models.SwipeMenu
import kotlinx.android.synthetic.main.content_list.*


class ListActivity : AppCompatActivity() {

    private lateinit var adapter: ListViewAdapter

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

        lv_accounts.setOnMenuItemClickListener { position, menu, index ->
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
        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(account.description)
        dialog.setMessage(R.string.app_remove_question)

        dialog.setPositiveButton(R.string.app_yes) { dialog, which ->
            deleteAccoun(account)
            dialog.dismiss()
        }
        dialog.setNegativeButton(R.string.app_no) { dialog, which ->
            dialog.cancel()
            dialog.dismiss()
        }

        dialog.create().show()
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

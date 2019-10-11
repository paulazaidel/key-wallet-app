package com.paulazaidel.keywallet.Activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.paulazaidel.keywallet.DataBase.AppDatabase
import com.paulazaidel.keywallet.Extensions.decrypt
import com.paulazaidel.keywallet.Extensions.encrypt
import com.paulazaidel.keywallet.Models.Account
import com.paulazaidel.keywallet.R

import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*
import java.lang.Exception


class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAccountFromIntent()

        fab.setOnClickListener { _ ->
            if (validateForm())
                saveAccount()
        }
    }

    private fun getAccountFromIntent() {
        if (intent!!.extras?.containsKey("account") != null) {
            val account = intent?.extras?.getSerializable("account") as? Account

            if (account != null) {
                txt_acc_id.setText(account.id.toString())
                edt_acc_description.setText(account.description)
                edt_acc_username.setText(account.username)
                edt_acc_password.setText(account.password.decrypt())
                edt_acc_url.setText(account.url)

                title = resources.getText(R.string.title_activity_edit)
            }
        }
    }

    private fun saveAccount() {
        val id = txt_acc_id.text.toString().toLong()
        val description = edt_acc_description.text.toString()
        val username = edt_acc_username.text.toString()
        val password = edt_acc_password.text.toString()
        val url = edt_acc_url.text.toString()

        val account = Account(id, username, password.encrypt(), description, url)

        if (id > 0)
            AppDatabase.getAppDatabase(this).accountDao().update(account)
        else
            AppDatabase.getAppDatabase(this).accountDao().insert(account)

        Toast.makeText(this, getString(R.string.app_add_successfully), Toast.LENGTH_SHORT)
            .show()

        finish()
    }

    private fun validateForm(): Boolean {
        if (!validateTextInputRequired(ip_acc_description, edt_acc_description))
            return false
        else if (!validateTextInputRequired(ip_acc_username, edt_acc_username))
            return false
        else if (!validateTextInputRequired(ip_acc_password, edt_acc_password))
            return false

        return true
    }

    private fun validateTextInputRequired(
        textInput: TextInputLayout,
        edtText: EditText
    ): Boolean {
        return if (edtText.text.toString().trim().isEmpty()) {
            textInput.error = getString(R.string.app_err_required)
            edtText.requestFocus()
            false
        } else {
            textInput.isErrorEnabled = false
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else return super.onOptionsItemSelected(item)
    }
}

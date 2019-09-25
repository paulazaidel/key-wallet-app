package com.paulazaidel.mypasswords.Activities

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.paulazaidel.mypasswords.DataBase.AppDatabase
import com.paulazaidel.mypasswords.Extensions.encrypt
import com.paulazaidel.mypasswords.Models.Account
import com.paulazaidel.mypasswords.R

import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*


class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if (validateForm())
                saveAccount()
        }
    }

    private fun saveAccount() {
        val id = txt_acc_id.text.toString().toLong()
        val description = edt_acc_description.text.toString()
        val username = edt_acc_username.text.toString()
        val password = edt_acc_password.text.toString()
        val url = edt_acc_url.text.toString()

        val account = Account(id, username, password.encrypt(), description, url)

        AppDatabase.getAppDatabase(this).accountDao().insert(account)

        Toast.makeText(this, getString(R.string.app_add_successfully), Toast.LENGTH_SHORT).show()

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

    private fun validateTextInputRequired(textInput: TextInputLayout, edtText: EditText): Boolean {
        return if (edtText.text.toString().trim().isEmpty()) {
            textInput.error = getString(R.string.app_err_required)
            edtText.requestFocus()
            false
        } else {
            textInput.isErrorEnabled = false
            true
        }
    }
}

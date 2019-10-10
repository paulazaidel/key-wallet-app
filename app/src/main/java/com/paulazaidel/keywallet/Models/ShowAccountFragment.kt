package com.paulazaidel.keywallet.Models

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.paulazaidel.keywallet.Extensions.decrypt
import com.paulazaidel.keywallet.R
import kotlinx.android.synthetic.main.show_account_layout.view.*

class ShowAccountFragment : DialogFragment {

    var account: Account

    constructor(account: Account) {
        this.account = account
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val view = activity?.layoutInflater?.inflate(R.layout.show_account_layout, null)

        view?.edt_show_username?.setText(account.username)
        view?.edt_show_password?.setText(account.password.decrypt())
        view?.edt_show_url?.setText(account.url)

        val alert = AlertDialog.Builder(activity)
        alert.setTitle(account.description)
        alert.setView(view)

        alert.setPositiveButton("OK") { dialog, _ ->
            dialog.cancel()
            dialog.dismiss()
        }
        return alert.create()
    }
}
package com.paulazaidel.mypasswords.Models

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.paulazaidel.mypasswords.R
import kotlinx.android.synthetic.main.card_view_layout.view.*

class ListViewAdapter : BaseAdapter {
    private var context: Activity
    private var accounts: List<Account>

    constructor(context: Activity, accounts: List<Account>) {
        this.context = context
        this.accounts = accounts
    }

    override fun getCount(): Int {
        return accounts.count()
    }

    override fun getItem(position: Int): Account {
        return accounts[position]
    }

    override fun getItemId(position: Int): Long {
        return accounts[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = context.layoutInflater.inflate(R.layout.card_view_layout, parent, false)

        view.txt_list_description.text = accounts[position].description
        view.txt_list_username.text = accounts[position].username

        return view;
    }
}
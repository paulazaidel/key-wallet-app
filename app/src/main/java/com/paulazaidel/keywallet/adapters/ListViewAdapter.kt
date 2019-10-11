package com.paulazaidel.keywallet.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.paulazaidel.keywallet.helpers.toString
import com.paulazaidel.keywallet.R
import com.paulazaidel.keywallet.models.Account
import kotlinx.android.synthetic.main.list_item_layout.view.*


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
        var view = context.layoutInflater.inflate(R.layout.list_item_layout, parent, false)

        val account = accounts[position]
        val generator = ColorGenerator.MATERIAL
        val firstLetter = account.description.get(0)
        val color = generator.getColor(firstLetter)

        val drawable = TextDrawable.builder()
            .buildRound(firstLetter.toString(), color)

        view.txt_list_description.text = account.description.toString(17)
        view.txt_list_username.text = account.username.toString(17)
        view.img_list_view.setImageDrawable(drawable)

        return view
    }
}
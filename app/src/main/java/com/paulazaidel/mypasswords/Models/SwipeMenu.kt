package com.paulazaidel.mypasswords.Models

import android.app.Activity
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.paulazaidel.mypasswords.R


class SwipeMenu : SwipeMenuCreator {

    private var context: Activity

    constructor(context: Activity) {
        this.context = context
    }

    override fun create(menu: SwipeMenu?) {
        var editItem = SwipeMenuItem(context)

        editItem.background = ContextCompat.getDrawable(context ,R.color.colorPrimaryLight)
        editItem.width = dpTopx(100)
        editItem.setIcon(R.drawable.ic_edit)
        menu?.addMenuItem(editItem)

        val deleteItem = SwipeMenuItem(context)
        deleteItem.background = ContextCompat.getDrawable(context ,R.color.colorPrimary)
        deleteItem.width = dpTopx(100)
        deleteItem.setIcon(R.drawable.ic_delete)
        menu?.addMenuItem(deleteItem)
    }

    private fun dpTopx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            context.getResources().getDisplayMetrics()
        ).toInt()
    }
}
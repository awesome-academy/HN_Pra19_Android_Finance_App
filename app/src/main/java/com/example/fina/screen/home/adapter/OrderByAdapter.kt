package com.example.fina.screen.home.adapter

import com.example.fina.R
import android.content.Context
import com.example.fina.utils.OrderBy
import com.example.fina.utils.base.BaseSpinnerAdapter

class OrderByAdapter(context: Context, orderByOptions: ArrayList<OrderBy>) :
    BaseSpinnerAdapter<OrderBy>(context, orderByOptions, R.layout.custom_spinner) {

    override fun getItemText(item: OrderBy?): String {
        return context.getString(item!!.getNameResId())
    }
}

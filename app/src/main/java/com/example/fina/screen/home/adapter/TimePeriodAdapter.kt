package com.example.fina.screen.home.adapter

import com.example.fina.R
import android.content.Context
import com.example.fina.utils.TimePeriod
import com.example.fina.utils.base.BaseSpinnerAdapter

class TimePeriodAdapter(context: Context, timePeriodOptions: ArrayList<TimePeriod>) :
    BaseSpinnerAdapter<TimePeriod>(context, timePeriodOptions, R.layout.custom_spinner) {

    override fun getItemText(item: TimePeriod?): String {
        return context.getString(item!!.getNameResId())
    }
}

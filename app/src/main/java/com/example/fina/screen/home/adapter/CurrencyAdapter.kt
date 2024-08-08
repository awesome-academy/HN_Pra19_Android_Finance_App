package com.example.fina.screen.home.adapter

import com.example.fina.R
import android.content.Context
import com.example.fina.data.model.Currency
import com.example.fina.utils.base.BaseSpinnerAdapter

class CurrencyAdapter(context: Context, currencies: ArrayList<Currency>) :
    BaseSpinnerAdapter<Currency>(context, currencies, R.layout.custom_spinner) {

    override fun getItemText(item: Currency?): String {
        return item?.symbol ?: ""
    }
}

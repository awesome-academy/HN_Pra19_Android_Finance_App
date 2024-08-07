package com.example.fina.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fina.R
import com.example.fina.utils.OrderBy

class OrderByAdapter(context: Context, orderByOptions: ArrayList<OrderBy>) :
    ArrayAdapter<OrderBy>(context, R.layout.custom_spinner, orderByOptions) {
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(
                R.layout.custom_spinner, parent, false,
            )

        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.textviewSpinner)
        textView.text = item?.getNameResId()?.let { context.getString(it) }
        return view
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(
                R.layout.custom_spinner, parent, false,
            )

        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.textviewSpinner)
        textView.text = item?.getNameResId()?.let { context.getString(it) }
        return view
    }
}

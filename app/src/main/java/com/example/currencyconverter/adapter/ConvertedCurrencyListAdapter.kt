package com.example.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import java.util.*

class ConvertedCurrencyListAdapter(private var context:Context, private var previousAmountList: ArrayList<Any>): RecyclerView.Adapter<ConvertedCurrencyListAdapter.ViewHolder>() {

    //for modifying the data
    fun setPreviousAmountList(previousAmountList: ArrayList<Any>)
    {
        this.previousAmountList = previousAmountList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mCurrencyName.text = previousAmountList[position].toString()
    }

    override fun getItemCount(): Int {
        return if (previousAmountList != null)
                    previousAmountList.size
                else
                    0
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mCurrencyName: TextView = itemView.findViewById(R.id.currencyName)
    }
}
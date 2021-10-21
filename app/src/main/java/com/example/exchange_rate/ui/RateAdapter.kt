package com.example.exchange_rate.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exchange_rate.R
import com.example.exchange_rate.data.RateData
import java.text.SimpleDateFormat

class RateAdapter(private val layoutInflater: LayoutInflater): RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    private val items = mutableListOf<RateData>()

    class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate = itemView.findViewById<TextView>(R.id.tv_data)
        private val textViewRate = itemView.findViewById<TextView>(R.id.tv_rate)
        @SuppressLint("SimpleDateFormat")
        private val formatter = SimpleDateFormat("dd.MM.yyyy")

        fun bind(item: RateData){
            textViewDate.text = formatter.format(item.date)
            textViewRate.text = item.valueRate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(layoutInflater.inflate(R.layout.item_rate, parent, false))
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(rateList: List<RateData>) {
        items.clear()
        items.addAll(rateList)
        notifyDataSetChanged()
    }
}

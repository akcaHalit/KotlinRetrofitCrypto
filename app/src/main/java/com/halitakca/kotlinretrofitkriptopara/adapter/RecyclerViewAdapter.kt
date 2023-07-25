package com.halitakca.kotlinretrofitkriptopara.adapter

import android.graphics.Color
import android.inputmethodservice.Keyboard.Row
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halitakca.kotlinretrofitkriptopara.R
import com.halitakca.kotlinretrofitkriptopara.databinding.RecyclerRowBinding
import com.halitakca.kotlinretrofitkriptopara.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")


    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener{
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

        holder.binding.nameText.text = cryptoList.get(position).currency
        holder.binding.priceText.text = cryptoList.get(position).price


    }



    override fun getItemCount(): Int {
        return cryptoList.count()
    }



}
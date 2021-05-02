package com.supremehyo.mvvmProject.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R

class CustomAdapter(private val context: Context, private val dataList: ArrayList<AccountBookContacts> , private  val itemClick: (AccountBookContacts) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View , val itemClick : (AccountBookContacts)->Unit) : RecyclerView.ViewHolder(itemView) {
        private val userPhoto = itemView.findViewById<ImageView>(R.id.userImg)
        private val userName = itemView.findViewById<TextView>(R.id.userNameTxt)
        private val userPay = itemView.findViewById<TextView>(R.id.payTxt)
        private val userAddress: TextView = itemView.findViewById<TextView>(R.id.addressTxt)

        fun bind(dataVo: AccountBookContacts, context: Context) {
            Log.v("adsf",dataVo.memo)
            //TextView에 데이터 세팅
            userName.text = dataVo.where_pay
            userPay.text = dataVo.memo
            userAddress.text = dataVo.money

            itemView.setOnClickListener {
                itemClick(dataVo)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false)
        return ItemViewHolder(view , itemClick)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
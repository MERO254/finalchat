package com.example.finalchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class chatAdapter(var context: Context, var list: List<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var SENDER = 1
    var RECIEVER = 2
    class sender(view:View):RecyclerView.ViewHolder(view){
        var txtsender:TextView = view.findViewById(R.id.txtsender)
    }

    class reciever(view: View):RecyclerView.ViewHolder(view){
        var txtreciever:TextView = view.findViewById(R.id.txtreciever)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            var view = LayoutInflater.from(context).inflate(R.layout.sender, parent, false)
            return sender(view)
        }else{
            var view  = LayoutInflater.from(context).inflate(R.layout.reciever, parent, false)
            return reciever(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == sender::class.java){
            var data = list[position]
            holder as sender
            holder.txtsender.text = data.message
        }else{
            var data = list[position]
            holder as reciever

            holder.txtreciever.text = data.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        var f = list[position]
        if(f.userid == "${FirebaseAuth.getInstance().currentUser?.uid}"){
            return SENDER
        }else{
            return RECIEVER
        }
    }
}
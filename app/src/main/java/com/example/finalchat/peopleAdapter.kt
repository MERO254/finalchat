package com.example.finalchat

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class peopleAdapter(var context:Context, var list:List<people>):RecyclerView.Adapter<peopleAdapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.imageView)
        var cardim:CardView = view.findViewById(R.id.imagecard)
        var name:TextView = view.findViewById(R.id.txtname)
        var lasttext:TextView = view.findViewById(R.id.txtlasttext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.profile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = list[position]

        holder.name.text = data.name

        holder.cardim.setOnClickListener {
            Toast.makeText(context, "imageview", Toast.LENGTH_SHORT).show()
            var view = ImageView(context)
            var dialog = AlertDialog.Builder(context)
            dialog.setTitle(data.name)
                .setView(view)
                .setPositiveButton("close"){ d,dialog ->
                      d.dismiss()
                }
            Picasso.get()
                .load(data.image)
                .into(view)
            dialog.create().show()

        }

        holder.itemView.setOnClickListener {
            var intent = Intent(context, chatActivity::class.java)
            intent.putExtra("recievername",data.name!!)
            intent.putExtra("userid", data.userid!!)
            context.startActivity(intent)
        }

        Picasso.get()
            .load(data.image)
            .into(holder.image)


    }

    fun searchAdd(list:List<people>){
        this.list = list
        notifyDataSetChanged()
    }
}
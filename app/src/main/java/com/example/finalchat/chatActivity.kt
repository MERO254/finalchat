package com.example.finalchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatActivity : AppCompatActivity() {

    lateinit var adapter: chatAdapter
    lateinit var db:FirebaseDatabase
    lateinit var list:MutableList<message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list = mutableListOf<message>()

        var imageback: ImageView = findViewById(R.id.imgback)
        var recievername:TextView = findViewById(R.id.txtchatname)
        var recyclerView:RecyclerView = findViewById(R.id.recyclerchat)
        var imgsend:ImageView = findViewById(R.id.imgsend)
        var edtmessage:EditText = findViewById(R.id.edtmassage)


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = chatAdapter(this,list)
        recyclerView.adapter = adapter

        var name = intent.getStringExtra("recievername")

        recievername.text = name

        imageback.setOnClickListener {
            var intent = Intent(this,peopleActivity::class.java)
            startActivity(intent)
        }

        recieveMessage()

        imgsend.setOnClickListener {
            var message = edtmessage.text.toString()
            sendMessage(message)
            adapter.notifyDataSetChanged()
        }


    }

    fun sendMessage(message:String){
        list.clear()
        var recieverid = intent.getStringExtra("userid")
        var name = intent.getStringExtra("recievername")

        var senderiddb = FirebaseAuth.getInstance().currentUser?.uid + recieverid
        var recieveriddb = recieverid + FirebaseAuth.getInstance().currentUser?.uid

        var message = message(name, message, FirebaseAuth.getInstance().currentUser?.uid)

        db = FirebaseDatabase.getInstance()
        db.getReference().child("messages").child("${senderiddb}").push().setValue(message).addOnSuccessListener {
            db.getReference().child("messages").child("${recieveriddb}").push().setValue(message)
        }

    }

    fun recieveMessage(){

        list.clear()

        var recieverid = intent.getStringExtra("userid")

        var senderiddb = FirebaseAuth.getInstance().currentUser?.uid + recieverid

        db = FirebaseDatabase.getInstance()
        db.getReference().child("messages").child("${senderiddb}").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children){
                    if(data != null){
                        var value = data.getValue(message::class.java)
                        list.add(value!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("message", "${error}")
            }

        })
    }


}
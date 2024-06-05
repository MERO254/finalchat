package com.example.finalchat

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class peopleActivity : AppCompatActivity() {

    lateinit var db:FirebaseDatabase
    lateinit var list:MutableList<people>
    lateinit var adapter: peopleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_people)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list = mutableListOf<people>()

        var searchView:SearchView = findViewById(R.id.searchview)
        var recyclerView:RecyclerView = findViewById(R.id.recyclerpeople)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = peopleAdapter(this, list)

        recyclerView.adapter = adapter


        db = FirebaseDatabase.getInstance()

        db.getReference().child("people").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children){
                    var value = data.getValue(people::class.java)
                    if(value != null){
                        list.add(value)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("people", "${error}")
            }

        })


        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText!!)
                return true
            }

        })
    }

    fun search(word:String){
        var s = mutableListOf<people>()
        for(i in list){
            if(i.name!!.toLowerCase().contains(word)){
                s.add(i)
            }
        }

        adapter.searchAdd(s)

    }
}
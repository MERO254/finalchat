package com.example.finalchat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var viewpager:ViewPager2 = findViewById(R.id.viewpager)
        var tablayout:TabLayout = findViewById(R.id.tablayout)

        var adapter = fragmentAdapter(this)
        viewpager.adapter = adapter


        TabLayoutMediator(tablayout, viewpager){tab,position ->
            when(position){
                0 -> tab.text = "signup"
                1 -> tab.text = "login"
            }
        }.attach()
    }
}
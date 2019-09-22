package com.example.playground.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.playground.R

class Rmap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rmap)

    }
    fun Rmapnextclick(v: View){
        startActivity(Intent(this,Rcompanyes::class.java))
    }
}


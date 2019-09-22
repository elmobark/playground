package com.example.playground


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch

import androidx.appcompat.app.AppCompatActivity

import com.example.playground.activitys.Rlogin


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s = Switch(this)
        s.setOnCheckedChangeListener { buttonView, isChecked -> }
        s.setOnClickListener { }
        val `in` = Intent(this, Rlogin::class.java)
        startActivity(`in`)
        startActivity(Intent(this, Rlogin::class.java))
    }
}
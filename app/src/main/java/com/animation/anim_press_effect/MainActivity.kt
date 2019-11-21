package com.animation.anim_press_effect

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addNewAlarm(v: View) {
        Toast.makeText(baseContext, "a new Alarm added!", Toast.LENGTH_SHORT).show()
    }
}

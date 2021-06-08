package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity", "app Started")
        val vm = ViewModelProvider(this).get(ViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = vm
        }
        vm.binding = binding
        Log.i("MainActivity", "x: ${binding.joystickCenter.x}, y: ${binding.joystickCenter.y}")
        this.binding.joystickCenter.setOnTouchListener { v, event ->
            var x = 0F
            var y = 0F
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i("MainActivity", "image Position is : ${binding.joystickCenter.x} , ${binding.joystickCenter.y} and View is : ${v.x}, ${v.y}")
                }
                MotionEvent.ACTION_MOVE -> {
                    x = event.x
                    y = event.y
                    v.animate().x(event.rawX).y(event.rawY).setDuration(0).start();
//                    vm.joyStickOnChanged(x, y)
                    Log.i("MainActivity", "Move coordination is : $x , $y and View is : ${v.x}, ${v.y}")
                }
            }
            true
        }
        throttleBinding()
    }

    private fun throttleBinding() {
        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                    this@MainActivity,
                    "Progress is: " + seek.progress + "%",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}


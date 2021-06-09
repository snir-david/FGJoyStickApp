package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: ViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity", "app Started")
        vm = ViewModelProvider(this).get(ViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = vm
        }
        vm.binding = binding
        this.binding.joystick.setOnTouchListener { v, event ->
            val offset = 1000
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i("MainActivity", "image Position is : ${event.x} , ${event.y} and View is : ${v.x}, ${v.y}")
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y
                    val x = event.x
                    vm.setJoyStick(x, y)
                    binding.joystickCenter.animate().x(x).y(y + offset).setDuration(0).start()
                }
                MotionEvent.ACTION_UP -> {

                }
            }
            true
        }
        throttleBinding()
        rudderBinding()
        initComp()
    }

    private fun initComp() {
        binding.rudderSeek.progress = 50
        binding.throttleSeek.progress = 50
        binding.throttleSeek.rotation = 90F
    }

    private fun throttleBinding() {
        binding.throttleSeek.setOnSeekBarChangeListener(object :
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
                vm.setThrottle(seek.progress)
            }
        })
    }

    private fun rudderBinding() {
        binding.rudderSeek.setOnSeekBarChangeListener(object :
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
                vm.setRudder(seek.progress)
            }
        })
    }


}


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
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: ViewModel
    private var radius: Int = 445
    private var centerX: Int = 570
    private var centerY: Int = 400


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //creating VM for binding data
        vm = ViewModelProvider(this).get(ViewModel::class.java)
        //binding VM with View
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = vm
        }
        vm.binding = binding
        //set on - onTouch Listener for joystick movement
        this.binding.joystick.setOnTouchListener { v, event ->
            //offset for Y AXIS
            val offset = 1000
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i("MainActivity", "image Position is : ${event.x} , ${event.y} and View is : ${v.x}, ${v.y}")
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y
                    val x = event.x
                    //check if new x and y are in joystick circle
                    if (inBound(x, y)) {
                        vm.setJoyStick(x, y)
                        binding.joystickCenter.animate().x(x).y(y + offset).setDuration(0).start()
                    }
                }
                MotionEvent.ACTION_UP -> {

                }
            }
            true
        }
        //binding throttle and rudder
        throttleBinding()
        rudderBinding()
        //initialize component
        initComp()
    }

    private fun initComp() {
        //setting rudder to middle
        binding.rudderSeek.progress = 50
        binding.throttleSeek.progress = 0
        //rotate throttle seek bar
        binding.throttleSeek.rotation = 90F
    }
    /*** Binding seek progress with throttle value***/
    private fun throttleBinding() {
        //setting up onChange Listener for throttle
        binding.throttleSeek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {
                vm.setThrottle(seek.progress)
            }
        })
    }

    /*** Binding seek progress with rudder value***/
    private fun rudderBinding() {
        //setting up onChange Listener for rudder
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
                Log.i("Rudder", "${seek.progress}")
                vm.setRudder(seek.progress)
            }
        })
    }
    /*** checking if new joystick position is valid***/
    private fun inBound(x: Float, y: Float): Boolean {
        //calculating distance between current point to the middle of joystick center
        val result = sqrt((((x - centerX).toDouble()).pow(2.0) + ((y - centerY).toDouble()).pow(2.0)))
        //if it is bigger than radius return false
        if (result > radius)
            return false
        return true
    }
}


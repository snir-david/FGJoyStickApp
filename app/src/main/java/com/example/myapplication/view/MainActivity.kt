package com.example.myapplication.view


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.viewModel.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IJoystick {
    /***Data Members***/
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: ViewModel
    private lateinit var joystick: Joystick

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
        //initialize component
        initComp()
    }

    private fun initComp() {
        joystick = Joystick(this)
        //setting rudder to middle
        binding.rudderSeek.progress = 50
        binding.throttleSeek.progress = 0
        //rotate throttle seek bar
        binding.throttleSeek.rotation = 90F
        vm.centerX = joystick.centerX.toDouble()
        vm.centerY = joystick.centerY.toDouble()
        vm.radius = joystick.radiusBase.toDouble()
        //binding throttle and rudder
        throttleBinding()
        rudderBinding()
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
                vm.setRudder(seek.progress)
            }
        })
    }

    /*** Binding joystick with aileron and elevator values***/
    override fun onChange(x: Double, y: Double) {
        vm.setJoyStick(x, y)
    }
}


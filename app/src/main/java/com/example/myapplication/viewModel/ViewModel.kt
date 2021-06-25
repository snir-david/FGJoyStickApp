package com.example.myapplication.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Model
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors


class ViewModel() : ViewModel() {
    /****Data Members***/
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
    var centerX: Double = 0.0
    var centerY: Double = 0.0
    var radius: Double = 0.0
    lateinit var binding: ActivityMainBinding
    private var model: Model = Model()
    private val executor = Executors.newSingleThreadExecutor()

    @Volatile
    var connected = false

    /*** Connect to FlightGear Simulator according to given IP and Port ***/
    fun connect() {
        //checking if user enter data to IP and Port textBoxes
        if (!ip.value.isNullOrEmpty() && !port.value.isNullOrEmpty()) {
            //changing visibility of buttons
            binding.connectButton.visibility = View.INVISIBLE
            binding.disconnectButton.visibility = View.VISIBLE
            //creating thread and connect to server
            executor.execute {
                connected = model.connect(ip.value.toString(), port.value.toString().toInt())
            }
        }
    }

    /*** Disconnect to FlightGear Simulator according to given IP and Port ***/
    fun disconnect() {
        //changing visibility of buttons
        binding.connectButton.visibility = View.VISIBLE
        binding.disconnectButton.visibility = View.INVISIBLE
        //setting seek bars to the default
        binding.rudderSeek.progress = 50
        binding.throttleSeek.progress = 0
        model.disconnect()
    }

    /*** Sending joystick position to the Model ***/
    fun setJoyStick(x: Double, y: Double) {
        //normalize value for FG
        val currX = (x - centerX) / radius
        val currY = (y - centerY) / radius
        if (connected) {
            executor.execute {
                model.setJoyStick(currX, -currY)
            }
        }
    }

    /*** Sending throttle value to the Model ***/
    fun setThrottle(throttle: Int) {
        if (connected) {
            executor.execute {
                //normalize value for FG
                val tmp = throttle.toDouble() / (100).toDouble()
                model.setThrottle(tmp)
            }
        }
    }

    /*** Sending rudder value to the Model ***/
    fun setRudder(rudder: Int) {
        if (connected) {
            executor.execute {
                //normalize value for FG
                val tmp = (rudder - 50).toDouble() / (50).toDouble()
                model.setRudder(tmp)
            }
        }
    }
}


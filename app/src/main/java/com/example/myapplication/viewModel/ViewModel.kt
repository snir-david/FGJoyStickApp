package com.example.myapplication.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Model
import com.example.myapplication.databinding.ActivityMainBinding


class ViewModel() : ViewModel() {
    /****Data Members***/
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
    private var model: Model = Model()
    var centerX: Double = 0.0
    var centerY: Double = 0.0
    var radius: Double = 0.0
    lateinit var binding: ActivityMainBinding

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
            val modelThread = Thread {
                connected = model.connect(ip.value.toString(), port.value.toString().toInt())
            }
            modelThread.start()
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
            Thread {
                model.setJoyStick(currX, currY)
            }.start()
        }
    }

    /*** Sending throttle value to the Model ***/
    fun setThrottle(throttle: Int) {
        if (connected) {
            Thread {
                //normalize value for FG
                val tmp = throttle.toDouble() / (100).toDouble()
                model.setThrottle(tmp)
            }.start()
        }
    }

    /*** Sending rudder value to the Model ***/
    fun setRudder(rudder: Int) {
        if (connected) {
            Thread {
                //normalize value for FG
                val tmp = (rudder - 50).toDouble() / (50).toDouble()
                model.setRudder(tmp)
            }.start()
        }
    }
}


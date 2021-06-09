package com.example.myapplication

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding


class ViewModel() : ViewModel() {
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
    private var model: Model = Model()
    var connected = model.connected
    lateinit var binding: ActivityMainBinding

    fun connect() {
        Log.i("ViewModel", "Clicked! ${ip.value}")
        if (!ip.value.isNullOrEmpty() && !port.value.isNullOrEmpty()) {
            binding.connectButton.visibility = View.INVISIBLE
            binding.disconnectButton.visibility = View.VISIBLE
            val modelThread = Thread {
                model.connect(ip.value.toString(), port.value.toString().toInt())
            }
            modelThread.start()
        }
    }

    fun disconnect() {
        model.disconnect()
    }

    fun setJoyStick(x: Float, y: Float) {
        val centerX = 550
        val centerY = 550
        val radius = 500
        val currX = (x - centerX) / radius
        val currY = (y - centerY) / radius
        Log.i("normal", "normalize value : $currX, $currY")
        if (connected)
            model.setJoyStick(currX, currY)
    }

    fun setThrottle(throttle: Int) {
        if (connected)
            model.setThrottle((throttle - 50) / 50)
    }

    fun setRudder(rudder: Int) {
        if (connected)
            model.setRudder((rudder - 50) / 50)
    }

    private fun isValid(x: Float, y: Float): Boolean {
        if (x < -345 || x > 720)
            return false
        if (y < -345 || y > 720)
            return false
        return true
    }
}


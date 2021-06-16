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
    private val centerX: Int = 570
    private val centerY: Int = 400
    private val radius: Int = 445

    @Volatile
    var connected = false
    lateinit var binding: ActivityMainBinding

    fun connect() {
        Log.i("ViewModel", "Clicked! ${ip.value}")
        if (!ip.value.isNullOrEmpty() && !port.value.isNullOrEmpty()) {
            binding.connectButton.visibility = View.INVISIBLE
            binding.disconnectButton.visibility = View.VISIBLE
            val modelThread = Thread {
                connected = model.connect(ip.value.toString(), port.value.toString().toInt())
            }
            modelThread.start()
        }
    }

    fun disconnect() {
        binding.connectButton.visibility = View.VISIBLE
        binding.disconnectButton.visibility = View.INVISIBLE
        binding.rudderSeek.progress = 50
        binding.throttleSeek.progress = 0
        model.disconnect()
    }

    fun setJoyStick(x: Float, y: Float) {
        val currX = (x - centerX) / radius
        val currY = (y - centerY) / radius
        Log.i("normal", "normalize value : $currX, $currY")
        if (connected) {
            Thread {
                model.setJoyStick(currX.toDouble(), currY.toDouble())
            }.start()
        }
    }

    fun setThrottle(throttle: Int) {
        if (connected) {
            Thread {
                val tmp = throttle.toDouble() / (100).toDouble()
                Log.i("Throttle", "$tmp")
                model.setThrottle(tmp)
            }.start()
        }
    }

    fun setRudder(rudder: Int) {
        if (connected) {
            Thread {
                val tmp = (rudder - 50).toDouble() / (50).toDouble()
                model.setRudder(tmp)
            }.start()
        }
    }
}


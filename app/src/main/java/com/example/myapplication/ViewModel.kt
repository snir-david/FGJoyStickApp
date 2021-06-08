package com.example.myapplication

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding


class ViewModel() : ViewModel() {
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
    val throttle = MutableLiveData<String>()
    var joyStickX: Float = 0.0f
    var joyStickY: Float = 0.0f
    private var Model: Model = Model()
    lateinit var binding: ActivityMainBinding


    fun connect() {
        Log.i("ViewModel", "Clicked! ${ip.value}")
        binding.connectButton.visibility = View.INVISIBLE
        binding.disconnectButton.visibility = View.VISIBLE
        val modelThread = Thread {
            Model.connect(ip.value.toString(), port.value.toString().toInt())
        }
        modelThread.start()
    }

    fun onThrottleChanged() {

    }

    fun joyStickOnChanged(x: Float, y: Float) {
        val posXY = IntArray(2)
        binding.joystickCenter.getLocationInWindow(posXY)
        Log.i("ViewModel", "x: ${posXY[0]}  y: ${posXY[1]}")
        if (isValid(x, y)) {
            joyStickX = x
            joyStickY = y
            binding.joystickCenter.x = x
            binding.joystickCenter.y = y
        }
    }

    private fun isValid(x: Float, y: Float): Boolean {
        if (x < -345 || x > 720)
            return false
        if (y < -345 || y>720)
            return false
        return true
    }
}


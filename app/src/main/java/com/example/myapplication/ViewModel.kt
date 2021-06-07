package com.example.myapplication

import android.opengl.Visibility
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import java.net.InetSocketAddress
import java.net.Socket


class ViewModel() : ViewModel() {
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
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

}
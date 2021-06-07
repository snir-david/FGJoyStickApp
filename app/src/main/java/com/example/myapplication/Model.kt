package com.example.myapplication

import android.net.InetAddresses
import android.util.Log
import android.widget.Toast
import java.net.InetSocketAddress
import java.net.Socket

class Model() {
    private lateinit var client: Socket

    fun connect(ipNumber: String, portNumber: Int) {
        try {
            val client = Socket()
            this.client = client
            this.client.connect(InetSocketAddress(ipNumber, portNumber))
            Log.i("Model", "Connected!")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}
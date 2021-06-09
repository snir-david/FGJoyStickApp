package com.example.myapplication

import android.util.Log
import java.net.InetSocketAddress
import java.net.Socket

class Model {
    private lateinit var client: Socket
    var connected = false

    fun connect(ipNumber: String, portNumber: Int) {
        try {
            val client = Socket()
            this.client = client
            this.client.connect(InetSocketAddress(ipNumber, portNumber))
            connected = true
            Log.i("Model", "Connected!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        client.close()
    }

    fun setJoyStick(aileron: Float, elevator: Float) {
        val aileronString = "set /controls/flight/aileron $aileron\r\n"
        val elevatorString = "set /controls/flight/aileron $elevator\r\n"
        client.outputStream.write(aileronString.toByteArray())
        client.outputStream.write(elevatorString.toByteArray())
    }

    fun setThrottle(throttle: Int) {
        val throttleString = "set /controls/flight/throttle $throttle\r\n"
        client.outputStream.write(throttleString.toByteArray())
    }

    fun setRudder(rudder: Int) {
        val rudderString = "set /controls/flight/throttle $rudder\r\n"
        client.outputStream.write(rudderString.toByteArray())
    }

}
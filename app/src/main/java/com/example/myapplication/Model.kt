package com.example.myapplication

import android.util.Log
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class Model {
    private lateinit var client: Socket
    private lateinit var outPrinter: PrintWriter
    @Volatile var connected = false

    fun connect(ipNumber: String, portNumber: Int): Boolean {
        try {
            val client = Socket()
            this.client = client
            this.client.connect(InetSocketAddress(ipNumber, portNumber))
            outPrinter = PrintWriter(client.getOutputStream(), true)
            connected = true
            Log.i("Model", "Connected!")
            return connected
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun disconnect() {
        client.close()
    }

    fun setJoyStick(aileron: Double, elevator: Double) {
        outPrinter.print("set /controls/flight/aileron ${aileron}\n")
        outPrinter.flush()
        outPrinter.print("set /controls/flight/elevator ${elevator}\r\n")
        outPrinter.flush()
        Log.i("Model", "Joystick set up to ${aileron}, ${elevator}!")

    }

    fun setThrottle(throttle: Double) {
        outPrinter.print("set /controls/engines/current-engine/throttle ${throttle}\r\n")
        outPrinter.flush()
        Log.i("Model", "Throttle set up to ${throttle}!")

    }

    fun setRudder(rudder: Double) {
        outPrinter.print("set /controls/flight/rudder ${rudder}\r\n")
        outPrinter.flush()
        Log.i("Model", "Rudder set up to ${rudder}!")
    }

}
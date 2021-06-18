package com.example.myapplication.model

import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class Model {
    /***Data Members***/
    private lateinit var client: Socket
    private lateinit var outPrinter: PrintWriter

    @Volatile
    var connected = false

    /*** Connect to FlightGear Simulator according to given IP and Port ***/
    fun connect(ipNumber: String, portNumber: Int): Boolean {
        //try to create socket and outPrinter
        return try {
            val client = Socket()
            this.client = client
            this.client.connect(InetSocketAddress(ipNumber, portNumber))
            outPrinter = PrintWriter(client.getOutputStream(), true)
            connected = true
            connected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /*** Disconnect to FlightGear Simulator according to given IP and Port ***/
    fun disconnect() {
        //close socket
        client.close()
    }

    /*** Sending joystick position to the FG ***/
    fun setJoyStick(aileron: Double, elevator: Double) {
        outPrinter.print("set /controls/flight/aileron ${aileron}\n")
        outPrinter.flush()
        outPrinter.print("set /controls/flight/elevator ${elevator}\r\n")
        outPrinter.flush()
    }

    /*** Sending throttle value to the FG ***/
    fun setThrottle(throttle: Double) {
        outPrinter.print("set /controls/engines/current-engine/throttle ${throttle}\r\n")
        outPrinter.flush()

    }

    /*** Sending rudder value to the FG ***/
    fun setRudder(rudder: Double) {
        outPrinter.print("set /controls/flight/rudder ${rudder}\r\n")
        outPrinter.flush()
    }

}
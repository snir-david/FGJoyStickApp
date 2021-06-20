package com.example.myapplication.view

interface IJoystickListener {
        fun onChange(x: Double, y: Double)
        fun onLoad(centerX: Double, centerY:Double, radius:Double)
}
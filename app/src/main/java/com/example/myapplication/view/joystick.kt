package com.example.myapplication.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.pow
import kotlin.math.sqrt

class Joystick : SurfaceView, SurfaceHolder.Callback, OnTouchListener {
    //joystick interface
    interface IJoystick {
        fun onChange(a: Double, e: Double)
    }

    private var call_back: IJoystick? = null
    private var xCen = 0f
    private var yCen = 0f
    private var radiusBase = 0f
    private var radiusH = 0f

    //constructor for joystick
    constructor(context: Context?) : super(context) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            call_back = context
        }
    }

    constructor(context: Context?, att: AttributeSet?, style: Int) : super(context, att, style) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            call_back = context
        }
    }

    constructor(context: Context?, att: AttributeSet?) : super(context, att) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            call_back = context
        }
    }

    private fun setupPosition() {
        xCen = width.toFloat() / 2
        yCen = height.toFloat() / 2
        radiusBase = width.coerceAtMost(height).toFloat() / 3
        radiusH = width.coerceAtMost(height).toFloat() / 5
    }

    private fun drawJoystick(x: Float, y: Float) {
        if (holder.surface.isValid) {
            // create canvas object for drawing
            val can = this.holder.lockCanvas()
            val paint_color = Paint()
            //clear canvas
            can.drawColor(Color.WHITE)
            //can.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            paint_color.setARGB(255, 150, 150, 150)
            //draw the circle
            can.drawCircle(xCen, yCen, radiusBase, paint_color)
            //draw the knob
            paint_color.setARGB(255, 120, 0, 120)
            can.drawCircle(x, y, radiusH, paint_color)
            //show the drawing to user
            holder.unlockCanvasAndPost(can)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setupPosition()
        drawJoystick(xCen, yCen)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun onTouch(v: View, e: MotionEvent): Boolean {
        if (v == this) {
            if (e.action != MotionEvent.ACTION_UP) {
                val disPlace =
                    sqrt((e.x - xCen).toDouble().pow(2.0) + (e.y - yCen).toDouble().pow(2.0))
                        .toFloat()
                //if the touch is in the joystick bounds
                if (disPlace < radiusBase) {
                    // move the joystick to the new location
                    drawJoystick(e.x, e.y)
                    call_back!!.onChange(e.x.toDouble(), e.y.toDouble())
                } else {
                    // calculate the new values
                    val proportion = radiusBase / disPlace
                    val newX = xCen + (e.x - xCen) * proportion
                    val newY = yCen + (e.y - yCen) * proportion
                    drawJoystick(newX, newY)
                    call_back!!.onChange(newX.toDouble(), newY.toDouble())
                }
            } else {
                //return the joystick to center
                drawJoystick(xCen, yCen)
                call_back!!.onChange(xCen.toDouble(), yCen.toDouble())
            }
        }
        return true
    }
}
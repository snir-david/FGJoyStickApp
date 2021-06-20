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
    /***Data Members***/
    lateinit var listener: IJoystick
    var centerX = 0f
    var centerY = 0f
    var radiusBase = 0f
    private var radiusH = 0f

    /***constructor for joystick***/
    constructor(context: Context?) : super(context) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            listener = context
        }
    }

    constructor(context: Context?, att: AttributeSet?, style: Int) : super(context, att, style) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            listener = context
        }
    }

    constructor(context: Context?, att: AttributeSet?) : super(context, att) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystick) {
            listener = context
        }
    }

    private fun setupPosition() {
        centerX = width.toFloat() / 2
        centerY = height.toFloat() / 2
        radiusBase = width.coerceAtMost(height).toFloat() / 3
        radiusH = width.coerceAtMost(height).toFloat() / 6
    }

    /***draw joystick on canvas***/
    private fun drawJoystick(x: Float, y: Float) {
        if (holder.surface.isValid) {
            // create canvas object for drawing
            val canvas = this.holder.lockCanvas()
            val paintColor = Paint()
            //clear canvas
            canvas.drawColor(Color.WHITE)
            //draw the circle - gray
            paintColor.setARGB(255, 145, 140, 140)
            canvas.drawCircle(centerX, centerY, radiusBase, paintColor)
            //draw the knob - purple
            paintColor.setARGB(255, 140, 100, 235)
            canvas.drawCircle(x, y, radiusH, paintColor)
            //show the drawing to user
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setupPosition()
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    /***onTouch event for joystick***/
    override fun onTouch(v: View, e: MotionEvent): Boolean {
        //checking if view is joystick
        if (v == this) {
            if (e.action != MotionEvent.ACTION_UP) {
                //calculating distance between touch and center - sqrt((x1-x2)^2 + (y1-y2)^2)
                val distance =
                    sqrt((e.x - centerX).toDouble().pow(2.0) + (e.y - centerY).toDouble().pow(2.0))
                        .toFloat()
                //if the touch is in the joystick bounds
                if (distance < radiusBase) {
                    // move the joystick to the new location
                    drawJoystick(e.x, e.y)
                    listener?.onChange(e.x.toDouble(), e.y.toDouble())
                } else {
                    // calculate the new values
                    val proportion = radiusBase / distance
                    val newX = centerX + (e.x - centerX) * proportion
                    val newY = centerY + (e.y - centerY) * proportion
                    drawJoystick(newX, newY)
                    listener?.onChange(newX.toDouble(), newY.toDouble())
                }
            } else {
                //return the joystick to center
                drawJoystick(centerX, centerY)
                listener?.onChange(centerX.toDouble(), centerY.toDouble())
            }
        }
        return true
    }
}
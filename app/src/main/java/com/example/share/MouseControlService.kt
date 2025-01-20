package com.example.share

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.view.accessibility.AccessibilityEvent
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Path
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

class MouseControlService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private var mousePointer: View? = null
    private val mainHandler = Handler(Looper.getMainLooper())


    private val mouseReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.share.CLICK_MOUSE") {
                val x = intent.getIntExtra("x", -1)
                val y = intent.getIntExtra("y", -1)
                Log.d("MouseControlService", "Received coordinates: x=$x, y=$y")
                simulateTap2(x, y)
//                moveMousePointer(x, y)
            }

            if (intent?.action == "com.example.share.MOVE_MOUSE") {
                val x = intent.getIntExtra("x", -1)
                val y = intent.getIntExtra("y", -1)
                Log.d("MouseControlService", "Received move coordinates: x=$x, y=$y")
                moveMousePointer(x, y)
            }
        }
    }

    private fun moveMousePointer(x: Int, y: Int) {
        mousePointer?.let { pointer ->
            val layoutParams = pointer.layoutParams as WindowManager.LayoutParams
            layoutParams.x = x
            layoutParams.y = y
            mainHandler.post { windowManager.updateViewLayout(pointer, layoutParams) }
        }
    }


    fun simulateTap2(x: Int, y: Int) {
        // Ensure any ongoing gestures are completed
        val path = Path().apply {
            moveTo(x.toFloat(), y.toFloat()) // Coordinates of the tap
        }

        // Create a GestureDescription
        val gesture = GestureDescription.Builder().apply {
            addStroke(GestureDescription.StrokeDescription(path, 0, 100)) // Duration: 100ms
        }.build()

        // Dispatch the gesture
        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
                Log.d("AccessibilityService", "Gesture Completed")
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                super.onCancelled(gestureDescription)
                Log.d("AccessibilityService", "Gesture Cancelled")
            }
        }, null)
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("MouseControlService", "Received accessibility event: $event")
    }

    override fun onInterrupt() {
        Log.d("MouseControlService", "Service interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("MouseControlService", "Service connected")
        setupPointer()
        val filter = IntentFilter("com.example.share.MOVE_MOUSE")
        registerReceiver(mouseReceiver, filter, RECEIVER_EXPORTED)
    }


    override fun onUnbind(intent: Intent?): Boolean {
        // Unregister the receiver when the service is unbound
        unregisterReceiver(mouseReceiver)
        Log.d("MouseControlService", "Service onUnbind and receiver unregistered")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver to prevent leaks
        unregisterReceiver(mouseReceiver)
        removePointer()
        Log.d("MouseControlService", "Service destroyed and receiver unregistered")
    }

    private fun setupPointer() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Inflate the floating mouse pointer layout
        mousePointer = LayoutInflater.from(this).inflate(R.layout.mouse_pointer, null)

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 0
        }

        windowManager.addView(mousePointer, layoutParams)
    }

    private fun removePointer() {
        mousePointer?.let {
            windowManager.removeView(it)
            mousePointer = null
        }
    }


}

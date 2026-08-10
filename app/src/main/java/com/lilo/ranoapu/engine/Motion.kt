package com.lilo.ranoapu.engine

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


import com.lilo.ranoapu.engine.Vector3


object Motion {

    private val acceleration = Vector3(0f, 0f, 0f)

    private lateinit var sensorManager: SensorManager
    private lateinit var listener: SensorEventListener

    fun start(context: Context, onAcceleration : () -> Unit) {

        sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE)
                    as SensorManager

        val accelerometer =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_LINEAR_ACCELERATION
            )

        listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                acceleration.X = event.values[0]
                acceleration.Y = event.values[1]
                acceleration.Z = event.values[2]
				
				if (acceleration.magnitude() >= 0.5f) {
					
					sensorManager.unregisterListener(listener)
                    onAcceleration()
                }
            
			
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) {
            }
        }

        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun getAcceleration(): Vector3 {
        return acceleration
    }
}
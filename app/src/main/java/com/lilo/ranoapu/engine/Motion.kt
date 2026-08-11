package com.lilo.ranoapu.engine

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


import com.lilo.ranoapu.engine.Vector3


object Motion {

    private val acceleration = Vector3(0.0, 0.0, 0.0)

    private lateinit var sensorManager: SensorManager
    private lateinit var listener: SensorEventListener
	
	private val callbacksAcc = mutableMapOf<String, (Vector3) -> Unit>()
	
	fun OnAccChanged(name: String, callback: (Vector3) -> Unit) {
		callbacksAcc[name] = callback
	}
	
	fun RemoveOnAccChanged(name: String) {
		callbacksAcc.remove(name)
	}

    fun StartAcc(context: Context) {

        sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE)
                    as SensorManager

        val accelerometer =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_LINEAR_ACCELERATION
            )

        listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                acceleration.X = event.values[0].toDouble()
                acceleration.Y = event.values[1].toDouble()
                acceleration.Z = event.values[2].toDouble()
				
				for ( (name, callback)  in callbacksAcc) {
					callback(acceleration)
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
	
	fun EndAcc() {
        if (! ::listener.isInitialized) return
        sensorManager.unregisterListener(listener)
        
    }

    fun GetAcceleration(): Vector3 {
        return acceleration
    }
}
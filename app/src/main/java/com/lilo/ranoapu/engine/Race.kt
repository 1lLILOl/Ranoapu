package com.lilo.ranoapu.engine

import android.Manifest
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission

object Race {


	private var running by mutableStateOf(false)
	private var beginTime = 0L
	private var beginPos = Vector3(0.0, 0.0, 0.0)
	private var lastPos = Vector3(0.0, 0.0, 0.0)
	private var elapsedTime = 0L
	
	
	private val handler = Handler(Looper.getMainLooper())
	
	private val timer = object : Runnable {
			
		override fun run() {
				
			val now = System.nanoTime()
			val currentTime = elapsedTime + (now - beginTime)
				
			RaceData.time = (currentTime) / 1_000_000_000.0

			RaceData.speed = (RaceData.displacement/RaceData.time) * 3.6
			RaceData.pace = 60 / RaceData.speed
				
			if (RaceData.displacement >= RaceData.maxDist ){

				RaceData.raceFinished = true
				Motion.removeOnAccChanged("StartRace")
				stopRace()
				return
			}
	        
			
			handler.postDelayed(this, 16)
		}
	}
	
	fun toggleRace(context : Context) {

		if (!RaceData.raceStarted) {

			resetRace()
			Motion.startAcc(context)
			beginPos = Gps.getPosition()
			lastPos = beginPos

			RaceData.raceStarted = true
		}

		if (RaceData.manualPause) {

			Motion.onAccChanged("StartRace", ::startRace)
			Gps.onLocationChanged("RaceUpdateLocation", ::updateLocation)
			
		} else {

			Motion.removeOnAccChanged("StartRace")
			Gps.removeOnLocChanged("RaceUpdateLocation")
			stopRace()
		}

		RaceData.manualPause = !RaceData.manualPause
		
	}

	private fun updateLocation(location: Location) {
		val currentPos = Gps.getPosition()

		if (location.accuracy <= 10f) {
			RaceData.distance = (currentPos - beginPos).magnitude()

			RaceData.displacement += (currentPos - lastPos).magnitude()
			lastPos = currentPos
		}

	}

	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startRace(context: Context, acc :Vector3) {

		if (acc.magnitude() < 1) {

			stopRace()
			RaceData.automaticPause = true
			return
		}
		
		if (!running) {

		    running = true
			RaceData.automaticPause = false
		    handler.post(timer)
			beginTime = System.nanoTime()
		}
	    
	}
	
	private fun stopRace() {
		
		if (!running) return
		
		running = false
		
		elapsedTime += System.nanoTime() - beginTime
		
		handler.removeCallbacks(timer)
	}

	fun resetRace() {

		Motion.removeOnAccChanged("StartRace")
		stopRace()
		RaceData.resetAllData()

		elapsedTime = 0
		lastPos = Vector3(0.0, 0.0, 0.0)

		Motion.endAcc()
	}
	
	
}
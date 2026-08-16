package com.lilo.ranoapu.engine

import android.Manifest
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.Context
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
			val currentPos = Gps.GetPosition()

			RaceData.distance = (currentPos - beginPos).magnitude()
			RaceData.displacement += (currentPos - lastPos).magnitude()
			
			lastPos = currentPos
				
			if (RaceData.displacement >= RaceData.maxDist ){

				RaceData.raceFinished = true
				Motion.RemoveOnAccChanged("StartRace")
				StopRace()
				return
			}
	        
			
			handler.postDelayed(this, 16)
		}
	}
	
	fun ToggleRace(context : Context) {

		if (!RaceData.raceStarted) {

			ResetRace()
			Motion.StartAcc(context)
			beginPos = Gps.GetPosition()

			RaceData.raceStarted = true
		}

		if (RaceData.manualPause) {

			Motion.OnAccChanged("StartRace", ::StartRace)
			
		} else {

			Motion.RemoveOnAccChanged("StartRace")
			StopRace()
		}

		RaceData.manualPause = !RaceData.manualPause
		
	}
	
	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun StartRace(context: Context, acc :Vector3) {
		
		Gps.UpdateLocation(context, onLocation = { _, ->})
			
		RaceData.speed = (RaceData.displacement/RaceData.time) * 3.6
		RaceData.pace = 60 / RaceData.speed
		

		if (acc.magnitude() < 1) {

			StopRace()
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
	
	private fun StopRace() {
		
		if (!running) return
		
		running = false
		
		elapsedTime += System.nanoTime() - beginTime
		
		handler.removeCallbacks(timer)
	}

	fun ResetRace() {

		Motion.RemoveOnAccChanged("StartRace")
		StopRace()
		RaceData.ResetAllData()

		elapsedTime = 0
		lastPos = Vector3(0.0, 0.0, 0.0)

		Motion.EndAcc()
	}
	
	
}
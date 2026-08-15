package com.lilo.ranoapu.engine

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.Context

import com.lilo.ranoapu.engine.Gps
import com.lilo.ranoapu.engine.Motion
import com.lilo.ranoapu.engine.Vector3

object Race {
	
	var maxDist by mutableStateOf(100)
	var time by mutableStateOf(0.0)
	var displacement by mutableStateOf(0.0)
	var distance by mutableStateOf(0.0)
	var pace by mutableStateOf(0.0)
	var speed by mutableStateOf(0.0)
	var runBtnMsg by mutableStateOf("Iniciar corrida")
	var running by mutableStateOf(false)
	
	private var firstRun = true
	private var beginTime = 0L
	private var beginPos = Vector3(0.0, 0.0, 0.0)
	private var lastPos = Vector3(0.0, 0.0, 0.0)
	private var elapsedTime = 0L
	
	
	private val handler = Handler(Looper.getMainLooper())
	
	private val timer = object : Runnable {
			
		override fun run() {
				
			val now = System.nanoTime()
			
			val currentTime = elapsedTime + (now - beginTime)
				
			time = (currentTime) / 1_000_000_000.0
			val currentPos = Gps.GetPosition()
				
			distance = (currentPos - beginPos).magnitude()
			displacement += (currentPos - lastPos).magnitude()
			
			lastPos = currentPos
				
			if (displacement >= maxDist ){
					
				Motion.RemoveOnAccChanged("StartRace")
				StopRace()
				runBtnMsg = "Corrida finalizada, reestarte"
				return
			}
	        
			
			handler.postDelayed(this, 16)
		}
	}
	
	fun ToggleRace(context : Context) {
		
		if (running) {
			
			Motion.RemoveOnAccChanged("StartRace")
			StopRace()
			runBtnMsg = "Iniciar corrida"
			
		} else {
			
			Motion.OnAccChanged("StartRace", ::StartRace)
		}
		
		if (firstRun) {
			Motion.StartAcc(context)
			Gps.Init(context)
		}
		
	}
	
	private fun StartRace(acc :Vector3) {
		
		Gps.UpdateLocation { location ->}
			
		speed = (displacement/time) * 3.6
		pace = 60 /speed
		

		if (acc.magnitude() < 1) {
			
			StopRace()
			runBtnMsg = "Pausa automática"
			return
		}
		
		if (firstRun) {
		    
		    beginPos = Gps.GetPosition()
			
			firstRun = false
		}
		
		if (!running) {
			
		    running = true
		    handler.post(timer)
		    runBtnMsg = "Pausar corrida"
			beginTime = System.nanoTime()
		}
	    
	}
	
	private fun StopRace() {
		
		if (!running) return
		
		running = false
		
		elapsedTime += System.nanoTime() - beginTime
		
		handler.removeCallbacks(timer)
	}
	
	fun EndRace() {
		
		StopRace()
		
		time = 0.0
		elapsedTime = 0L
		pace = 0.0
		displacement = 0.0
		distance = 0.0
		speed = 0.0
		
		runBtnMsg = "Iniciar corrida"
		firstRun = true
		Motion.EndAcc()
		
	}
	
	fun SetDist(value: Int) {
		maxDist = value
	}
	
	
}
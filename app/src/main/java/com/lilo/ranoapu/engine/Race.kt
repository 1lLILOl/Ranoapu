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
	
	var dist by mutableStateOf(100)
	var time by mutableStateOf(0.0)
	var reachedDist by mutableStateOf(0.0)
	var pace by mutableStateOf(0f)
	var speed by mutableStateOf(0f)
	var runBtnMsg by mutableStateOf("Iniciar corrida")
	var running by mutableStateOf(false)
	
	private var firstRun = true
	private var beginTime = 0L
	private var beginPos = Vector3(0.0, 0.0, 0.0)
	private var elapsedTime = 0L
	
	
	var X by mutableStateOf(0.0)
	var Y by mutableStateOf(0.0)
	var Z by mutableStateOf(0.0)
	
	private val handler = Handler(Looper.getMainLooper())
	
	private val timer = object : Runnable {
			
		override fun run() {
				
			val now = System.nanoTime()
			
			val currentTime = elapsedTime + (now - beginTime)
				
			time = (currentTime) / 1_000_000_000.0
			val currentPos = Gps.GetPosition()
				
			reachedDist = (currentPos - beginPos).magnitude()
				
			if ( reachedDist >= dist ){
					
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
			
		speed = Gps.GetSpeed()
		pace = 60f / (speed * 3.6f)
		

		if (acc.magnitude() < 1) {
			
			val pos = Gps.GetPosition()
			X = pos.X
			Y = pos.Y
			Z = pos.Z
			
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
		pace = 0f
		reachedDist = 0.0
		speed = 0f
		
		runBtnMsg = "Iniciar corrida"
		firstRun = true
		Motion.EndAcc()
		
	}
	
	fun SetDist(value: Int) {
		dist = value
	}
	
	
}
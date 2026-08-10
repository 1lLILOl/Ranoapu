package com.lilo.ranoapu.engine

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.lilo.ranoapu.engine.Gps
import com.lilo.ranoapu.engine.Motion
import com.lilo.ranoapu.engine.Vector3

object Race {
	
	var dist by mutableStateOf(100) private set
	var time by mutableStateOf (0) private set
	
	private var beginTime = 0L
	private var beginPos = 0
	
	
	private val handler = Handler(Looper.getMainLooper())
	
	private val timer = object : Runnable {
			
		override fun run() {
				
			time = (System.nanoTime() - beginTime) / 1_000_000_000.0
				
			if ( (currentPos - beginPos) >= dist ){
					
				EndRace()
				return
			}
	        
			
			handler.postDelayed(this, 16)
		}
	}
	
	fun TryStartRace(context : Context) {
		
		Motion.start(context) {
			StartRace()
		}
	}
	
	fun StartRace() {
		
		beginTime = System.nanoTime()
		beginPos = Gps.getPosition()
		
		time = 0f
		
		handler.post(timer)
	    
	}
	
	fun EndRace() {
		
		handler.removeCallbacks(timer)
	}
	
	fun SetDist(value: Int) {
		dist = value
	}
	
	
}
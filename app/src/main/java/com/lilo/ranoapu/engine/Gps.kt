package com.lilo.ranoapu.engine

import com.lilo.ranoapu.engine.Vector3

object Gps {
	
	private var Position : Vector3(0f, 0f, 0f)
    private var Speed : Vector3(0f, 0f, 0f) 
	

	fun GetPosition() : Vector3 {
	    return Position
    }
	
	fun GetSpeed() : Vector3 {
		return Speed
	}
	
	
}

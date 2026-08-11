package com.lilo.ranoapu.engine

import android.Manifest
import android.location.Location
import android.content.Context
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import kotlin.math.PI
import kotlin.math.cos


import com.lilo.ranoapu.engine.Vector3

object Gps {
	
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private lateinit var context: Context
		
	fun Init(context: Context) {
		this.context = context.applicationContext
	
		fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)
			
		UpdateLocation { loc ->
			axysZero = loc
		}
	}
	
	private lateinit var location: Location
	private var position = Vector3(0.0, 0.0, 0.0)
    private var speed = 0f
	private lateinit var axysZero: Location
	
	fun UpdateLocation(onLocation: (Location) -> Unit) {
		
		if (
		    ActivityCompat.checkSelfPermission(
			    context, Manifest.permission.ACCESS_FINE_LOCATION
			) != 
			PackageManager.PERMISSION_GRANTED
			&&
			
			ActivityCompat.checkSelfPermission(
			    context, Manifest.permission.ACCESS_COARSE_LOCATION
			) != 
			PackageManager.PERMISSION_GRANTED
		) {
			
		    return
		}
		
		fusedLocationClient.getCurrentLocation(
		    Priority.PRIORITY_HIGH_ACCURACY,
			null
		).addOnSuccessListener { loc ->
			
			if (loc != null) {
				location = loc
				onLocation(location)
			}
			
		}
	}
    
	

	fun GetPosition() : Vector3 {
		
		val X = (location.longitude - axysZero.longitude) * 
		    40075000 / 360 * 
		    cos(axysZero.latitude * PI / 180)
			
		val Y = (location.altitude - axysZero.altitude)
			
		val Z = (location.latitude - axysZero.latitude) * 
		    40075000 / 360
		
		position = Vector3(
		
		    X, Y, Z
		)
		
		
	    return position
    }
	
	fun GetSpeed() : Float {
		
		speed = location.speed
		
		return speed
	}
	
	
}

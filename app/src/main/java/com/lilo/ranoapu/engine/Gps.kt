package com.lilo.ranoapu.engine

import android.Manifest
import android.location.Location
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RequiresPermission

import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority


import kotlin.math.PI
import kotlin.math.cos


object Gps {
	
	private lateinit var fusedLocationClient: FusedLocationProviderClient


	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun init(context: Context) {

		fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

		updateLocation(context, onLocation = { loc ->
			axysZero = loc
		})

		startOnLocation()
	}
	
	private lateinit var location: Location
	private var position = Vector3(0.0, 0.0, 0.0)
	private lateinit var axysZero: Location
	
	private var hasPerms = false
	private fun hasPermissions(context: Context): Boolean {
		
		if (
		    ActivityCompat.checkSelfPermission(
			    context, Manifest.permission.ACCESS_FINE_LOCATION
			)
			!=
			PackageManager.PERMISSION_GRANTED

			&&
			
			ActivityCompat.checkSelfPermission(
			    context, Manifest.permission.ACCESS_COARSE_LOCATION
			)
			!=
			PackageManager.PERMISSION_GRANTED
		) {
			
		    return false
		}
		
		hasPerms = true
		
		return true
	}
	
	
	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
	fun updateLocation(context: Context, onLocation: (Location) -> Unit) {
		
		if (!hasPerms && !hasPermissions(context)) return
		
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

	private val callbacksLoc = mutableMapOf<String, (Location) -> Unit>()

	fun onLocationChanged(name: String, callback: (Location) -> Unit) {
		callbacksLoc[name] = callback
	}

	fun removeOnLocChanged(name: String) {
		callbacksLoc.remove(name)
	}

	private lateinit var locationCallback: LocationCallback
	private var isGettingLocation = false

	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun startOnLocation() {

        if (isGettingLocation) return
		isGettingLocation = true

		val request = LocationRequest.Builder(
			Priority.PRIORITY_HIGH_ACCURACY,
			1000L
		).build()

		locationCallback = object : LocationCallback() {

			override fun onLocationResult(result: LocationResult) {

				location = result.lastLocation ?: return

				for ((_, callback) in callbacksLoc) {
					callback(location)
				}
			}
		}

		fusedLocationClient.requestLocationUpdates(
			request,
			locationCallback,
			Looper.getMainLooper()
		)
		
	}

	fun endOnLocation() {
		
		if (!isGettingLocation) return
		isGettingLocation = false
		
		fusedLocationClient.removeLocationUpdates(locationCallback)
	}
    
	
	
	private const val METERSPERDEGREE = 40075000.0 / 360.0
	private const val DEGREETORAD = PI / 180.0

	fun getPosition() : Vector3 {
		
		val x = (location.longitude - axysZero.longitude) *
				METERSPERDEGREE *
		    cos(axysZero.latitude * DEGREETORAD)
			
		//val y = (location.altitude - axysZero.altitude)
			
		val z = (location.latitude - axysZero.latitude) *
				METERSPERDEGREE

		position = Vector3(
		
		    x, 0.0, z
		)
		
	    return position
    }

	fun getYPosition() : Double {
		return (location.altitude - axysZero.altitude)
	}

	
}

package com.lilo.ranoapu.engine

import android.Manifest
import android.location.Location
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.compose.ui.platform.LocalContext

import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import kotlin.math.PI
import kotlin.math.cos


object Gps {
	
	private lateinit var fusedLocationClient: FusedLocationProviderClient


	@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun Init(context: Context) {

		fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)
			
		UpdateLocation(context, onLocation = { loc ->
			axysZero = loc
		})
	}
	
	private lateinit var location: Location
	private var position = Vector3(0.0, 0.0, 0.0)
	private lateinit var axysZero: Location
	
	private var hasPerms = false
	private fun HasPermissions(context: Context): Boolean {
		
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
	fun UpdateLocation(context: Context, onLocation: (Location) -> Unit) {
		
		if (!hasPerms && !HasPermissions(context)) return
		
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
    
	
	
	private val metersPerDegree = 40075000 / 360
	private val degreeToRad = PI / 180

	fun GetPosition() : Vector3 {
		
		val x = (location.longitude - axysZero.longitude) *
		    metersPerDegree * 
		    cos(axysZero.latitude * degreeToRad)
			
		val y = (location.altitude - axysZero.altitude)
			
		val z = (location.latitude - axysZero.latitude) *
		    metersPerDegree
		
		position = Vector3(
		
		    x, y, z
		)
		
	    return position
    }

	
}

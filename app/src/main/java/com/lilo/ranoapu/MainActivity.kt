package com.lilo.ranoapu

import android.os.Bundle
import android.Manifest

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.google.android.gms.location.FusedLocationProviderClient

import com.lilo.ranoapu.ui.MainScreen
import com.lilo.ranoapu.engine.Gps

class MainActivity : ComponentActivity() {
	
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	
	private val locationPermissionRequest = registerForActivityResult(
	
	    ActivityResultContracts.RequestMultiplePermissions()
	) { permissions ->
		
		val fineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
		val coarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
		
		if (fineLocation || coarseLocation) {
			Gps.Init(this)
        }
    }
	
	
	
    override fun onCreate(savedInstanceState: Bundle?) {
		
        super.onCreate(savedInstanceState)
		
        enableEdgeToEdge()
		
		locationPermissionRequest.launch(arrayOf(
		
	        Manifest.permission.ACCESS_FINE_LOCATION,
		    Manifest.permission.ACCESS_COARSE_LOCATION
	    ))
		
        setContent {
            MainScreen()
        }
		
	
    }
}


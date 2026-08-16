package com.lilo.ranoapu.engine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object RaceData {

    var maxDist by mutableIntStateOf(100)
    var time = 0.0
    var displacement by mutableDoubleStateOf(0.0)
    var distance by mutableDoubleStateOf(0.0)
    var pace by mutableDoubleStateOf(0.0)
    var speed by mutableDoubleStateOf(0.0)
    var automaticPause by mutableStateOf(false)
    var raceStarted by mutableStateOf(false)
    var manualPause by mutableStateOf(true)
    var raceFinished by mutableStateOf(false)

    fun ResetAllData() {

        time = 0.0
        displacement = 0.0
        distance = 0.0
        pace = 0.0
        speed = 0.0
        raceStarted = false
        automaticPause = false
        manualPause = true
        raceFinished = false
    }
}
package com.mahmouddev.fusedlocation_example.fusedlocation


import android.location.Location

interface LocationManager {
    fun onLocationChanged(location: Location?)

    fun getLastKnownLocation(location: Location?)
}
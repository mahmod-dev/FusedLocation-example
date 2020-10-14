package com.mahmouddev.fusedlocation_example.geolocation


import android.location.Location

interface LocationManager {
    fun onLocationChanged(location: Location?)

    fun getLastKnownLocation(location: Location?)
}
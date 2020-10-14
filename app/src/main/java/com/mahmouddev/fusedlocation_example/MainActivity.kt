package com.mahmouddev.fusedlocation_example

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mahmouddev.fusedlocation_example.geolocation.GPS_REQUEST_CODE
import com.mahmouddev.fusedlocation_example.geolocation.LocationHelper
import com.mahmouddev.fusedlocation_example.geolocation.LocationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var locationHelper: LocationHelper
    private var lng: Double? = 0.0
    private var lat: Double? = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initGpsLocation()
    }


    private fun initGpsLocation() {
        locationHelper = LocationHelper(this, object : LocationManager {

            override fun onLocationChanged(location: Location?) {

                lat = location?.latitude
                lng = location?.longitude
                Log.e(TAG, "onLocationChanged latitude: ${location?.latitude}")
                Log.e(TAG, "onLocationChanged longitude: ${location?.longitude}")
                tvLatLng.text = " latitude: $lat \n longitude: $lng"

            }

            override fun getLastKnownLocation(location: Location?) {

                Log.e(TAG, "getLastKnownLocation latitude: ${location?.latitude}")
                Log.e(TAG, "getLastKnownLocation longitude: ${location?.longitude}")
                lat = location?.latitude
                lng = location?.longitude
                tvLatLng.text = " latitude: $lat \n longitude: $lng"

            }

        })

    }

    override fun onStop() {
        super.onStop()
        locationHelper.stopLocationUpdates()

    }

    override fun onResume() {
        super.onResume()

        if (locationHelper.checkLocationPermissions()) {
            if (locationHelper.checkMapServices()) {
                locationHelper.startLocationUpdates()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GPS_REQUEST_CODE) {
            initGpsLocation()
        }
    }


}
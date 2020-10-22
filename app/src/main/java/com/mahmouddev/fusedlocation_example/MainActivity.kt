package com.mahmouddev.fusedlocation_example

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mahmouddev.fusedlocation_example.fusedlocation.GPS_REQUEST_CODE
import com.mahmouddev.fusedlocation_example.fusedlocation.LocationHelper
import com.mahmouddev.fusedlocation_example.fusedlocation.LocationManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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

    @SuppressLint("SetTextI18n")
    private fun initGpsLocation() {
        locationHelper = LocationHelper(this, object : LocationManager {

            override fun onLocationChanged(location: Location?) {

                lat = location?.latitude
                lng = location?.longitude
                Log.e(TAG, "onLocationChanged latitude: ${location?.latitude}")
                Log.e(TAG, "onLocationChanged longitude: ${location?.longitude}")
                tvLatLng.text = " latitude: $lat \n longitude: $lng"
                tvAddress.text = getAddressDetails(lat!!, lng!!)

            }

            override fun getLastKnownLocation(location: Location?) {

                Log.e(TAG, "getLastKnownLocation latitude: ${location?.latitude}")
                Log.e(TAG, "getLastKnownLocation longitude: ${location?.longitude}")
                lat = location?.latitude
                lng = location?.longitude
                tvLatLng.text = " latitude: $lat \n longitude: $lng"
                tvAddress.text = getAddressDetails(lat!!, lng!!)

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

    private fun getAddressDetails(lat: Double, lng: Double): String? {
        val geoCoder = Geocoder(applicationContext, Locale.getDefault())
        val addresses: List<Address>
        var fullAddress = ""
        try {
            addresses = geoCoder.getFromLocation(lat, lng, 1)
            if (addresses.isNotEmpty()) {
                val address = addresses[0].getAddressLine(0)
                val locality = addresses[0].locality
                val subLocality = addresses[0].subLocality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName
                fullAddress =
                    "address: $address \n locality: $locality \n subLocality: $subLocality \n" +
                            "state: $state \n country: $country \n postalCode: $postalCode \n knownName: $knownName  "

                Log.e(TAG, "getAddressDetails: $fullAddress")
            } else
                fullAddress = " address unavailable in your country! "

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getAddressDetails Exception: ${e.message}")
            return null
        }
        return fullAddress


    }


}
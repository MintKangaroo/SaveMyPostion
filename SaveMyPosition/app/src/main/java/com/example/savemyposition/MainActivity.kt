package com.example.savemyposition

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView.onCreate(savedInstanceState)

        if (checkPermissions()) {
            initMap()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        myLocationButton.setOnClickListener { onMyLocationButtonClick() }

        val DataPage = Intent(this,SaveDataActivity::class.java)

        myLocationSaveButton.setOnClickListener { startActivity(DataPage)}
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        initMap()
    }

    private fun checkPermissions(): Boolean {

        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun initMap() {
        mapView.getMapAsync {

            googleMap = it
            it.uiSettings.isMyLocationButtonEnabled = false

            when {
                checkPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {

        val locationProvider: String = LocationManager.GPS_PROVIDER

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val lastKnownLocation: Location = locationManager.getLastKnownLocation(locationProvider)

        val currentDateTime = Calendar.getInstance().time

        var dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

        Toast.makeText(this, "${dateFormat}+${lastKnownLocation.latitude}+${lastKnownLocation.longitude}",Toast.LENGTH_LONG).show()

        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }

    fun onMyLocationButtonClick() {
        when {
            checkPermissions() -> googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
            )
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }



    

}

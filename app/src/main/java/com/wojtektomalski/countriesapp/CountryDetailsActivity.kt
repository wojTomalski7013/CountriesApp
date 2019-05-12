package com.wojtektomalski.countriesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_country_details.*

class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var mapFragment: SupportMapFragment
        lateinit var googleMap: GoogleMap

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        var mainActivity:MainActivity = MainActivity()
        var data = intent.extras
        var name = data.get("name").toString()
        var capital = data.get("capital").toString()
        var region:String = data.get("region").toString()
        var population:String = data.get("population").toString()
        var latlng:String = data.get("latlng").toString()

        nameTextView.text = "Name:$name"
        capitalTextView.text = "Capital$capital"
        regionTextView.text = "Region:$region"
        populationTextView.text = "Population:$population"

        latlng = latlng.substring(1, latlng.length - 1)
        val arr = latlng.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()


        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap  = it

            val countryLocation = LatLng(arr[0].toDouble(),arr[1].toDouble())
            googleMap.addMarker(MarkerOptions().position(countryLocation).title(name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(countryLocation,3f))
        })
    }
}

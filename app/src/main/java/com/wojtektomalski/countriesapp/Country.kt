package com.wojtektomalski.countriesapp

import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray

public class Country {
     var name:String? = null
     var capital:String? = null
     var region:String? = null
     var population:Double? = null
     var latlng:JSONArray? = null

    constructor(name: String?, capital: String?, region: String?, population: Double?,latlng:JSONArray) {

        this.name = name
        this.capital = capital
        this.region = region
        this.population = population
        this.latlng = latlng
    }

    override fun toString(): String {
        return "Country(name=$name, capital=$capital, region=$region, population=$population"
    }

}
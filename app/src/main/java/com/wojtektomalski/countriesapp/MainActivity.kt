package com.wojtektomalski.countriesapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var volleyRequest: RequestQueue? = null
    public var countriesMap = mutableMapOf<String,Country>()
    override fun onCreate(savedInstanceState: Bundle?) {
        volleyRequest = Volley.newRequestQueue(this)
        var countriesNames :Array<String> = getCountries("https://restcountries.eu/rest/v2/all")
        var countriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countriesNames)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countriesListViewID.adapter = countriesAdapter

        searchId.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                countriesAdapter.filter.filter(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        countriesListViewID.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this,CountryDetailsActivity::class.java)
            val name = parent.getItemAtPosition(position) as String
            var capital:String = countriesMap.get(name)!!.capital.toString()
            var region:String = countriesMap.get(name)!!.region.toString()
            var population:String = countriesMap.get(name)!!.population.toString()
            var latlng:String= countriesMap.get(name)!!.latlng.toString()
            intent.putExtra("name",name)
            intent.putExtra("capital",capital)
            intent.putExtra("region",region)
            intent.putExtra("population",population)
            intent.putExtra("latlng",latlng)

            startActivity(intent)
        }
    }

    fun getCountries(url:String):Array<String>{
        var countriesNames:Array<String> = Array<String>(250,{""})
        var name:String = "unkown"
        var capital:String = "unknown"
        var region:String = "unkown"
        var population:Double = 0.0
        var lanlng:JSONArray? = null

        val jsonArray = JsonArrayRequest(Request.Method.GET,url,Response.Listener {
                response : JSONArray -> try{
            for(i in 0..response.length()-1){
                val countryObj = response.getJSONObject(i)
                 name = countryObj.getString("name")
                 capital = countryObj.getString("capital")
                 region = countryObj.getString("region")
                 population = countryObj.getString("population").toDouble()

                var latlng = countryObj.getJSONArray("latlng")


                var country = Country(name,capital,region,population,latlng)
                countriesMap.put(name,country)
                countriesNames[i] = name
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        },Response.ErrorListener{
                error: VolleyError? ->  try{
            Log.d("Error",error.toString())
        }catch (e: JSONException){
            e.printStackTrace()
        }
        })
        volleyRequest!!.add(jsonArray)




        return  countriesNames
    }
}

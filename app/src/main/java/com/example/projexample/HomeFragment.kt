package com.example.projexample

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.projexample.databinding.ActivityMapsBinding
import com.example.projexample.model.YelpRestaurant
import com.example.projexample.model.YelpSearchResult
import com.example.projexample.service.SVCYelp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val YELP_KEY = "J0dsBRluxFk2aGoeqvKv4G4tceXQMHtR3arQq3_DBLbTAXDq20QDhXXqTj_4E2UCGQBg0WHpfaWt4MEIDOGCn8vXRdmAI02Tg0QopOELt2yDgzSpuNK8NKCruSVOYXYx"
private const val TAG = "MapsActivity"

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocation: LatLng
    private var range = 1000 //meters 1000 meters = 1km

    private val restaurants = mutableListOf<YelpRestaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val button = view.findViewById<View>(R.id.button) as Button
        button.setOnClickListener{
            val toast = Toast.makeText(
                activity,
                "Generate Restaurants and Update the Map",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocation()
    }

    private fun getLocation() {
        checkPermission()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    userLocation = LatLng(location.latitude, location.longitude)
                    //adds the marker
                    mMap.addMarker(MarkerOptions().position(userLocation))
                    //moves the camera when startup
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))

                    //Sets the zoom
                    mMap.setMaxZoomPreference(16f)
                    mMap.setMinZoomPreference(14f)

                    //loads restaurants after userLocation is known
                    getRestaurants()
                } else {
                    print("No Previous Location")
                }
            }
    }

    private fun checkPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
//            return
//        }
    }

    //Network request. Range == Meters
    private fun getRestaurants() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.yelp.com/v3/").addConverterFactory(GsonConverterFactory.create()).build()
        val svcYelp = retrofit.create(SVCYelp::class.java)
        svcYelp.searchRestaurants("Bearer $YELP_KEY", range, userLocation.longitude, userLocation.latitude).enqueue(object :
            Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                val body = response.body()
                if (body == null) {
                    Log.i(TAG, "Invalid Response Body from Yelp API")
                    return
                }
                restaurants.addAll(body.restaurants)
                displayRestaurants()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "Failed To Get Restaurants $t")
            }
        })
    }

    //marks each restaurant
    private fun displayRestaurants() {
        restaurants.forEach {
            mMap.addMarker(MarkerOptions().position(LatLng(it.coordinates.latitude, it.coordinates.longitude)))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings) {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item);
    }

}
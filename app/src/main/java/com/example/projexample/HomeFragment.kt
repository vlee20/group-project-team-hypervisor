package com.example.projexample

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.projexample.database.RestaurantDatabase
import com.example.projexample.databinding.FragmentHomeBinding
import com.example.projexample.model.YelpRestaurant
import com.example.projexample.model.YelpSearchResult
import com.example.projexample.service.SVCYelp
import com.example.projexample.viewmodel.RestaurantViewModel
import com.example.projexample.viewmodel.RestaurantViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import kotlin.random.Random


private const val YELP_KEY = "J0dsBRluxFk2aGoeqvKv4G4tceXQMHtR3arQq3_DBLbTAXDq20QDhXXqTj_4E2UCGQBg0WHpfaWt4MEIDOGCn8vXRdmAI02Tg0QopOELt2yDgzSpuNK8NKCruSVOYXYx"
private const val TAG = "MapsActivity"

class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentHomeBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocation: LatLng
    private val TAG = "HomeFragment"

    public val restaurants = mutableListOf<YelpRestaurant>()

    lateinit var settings: SharedPreferences
    var listTitles = mutableListOf<CharSequence>()
    var distin = listTitles.distinct().toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settings = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setFragmentResult("dataFromHome", bundleOf("list" to distin))
        binding.button.setOnClickListener {
            mMap.clear()
            getLocationNoCamera()
            val filter = settings.getString("category", "")
            val range = settings.getInt("rangeSelector", 10) //meters 1000 meters = 1km
            var listRestaurants = ArrayList<YelpRestaurant>()
            val randomFilteredRestaurant = getRandomFilteredRestaurantsList(listRestaurants, filter, range)
            displayFilteredRestaurantsButton(randomFilteredRestaurant, listRestaurants, filter, range)

            // moves the camera
            var randRestLocation = LatLng(
                randomFilteredRestaurant.coordinates.latitude,
                randomFilteredRestaurant.coordinates.longitude
            )


            for (names in listRestaurants) {
                for(titles in names.categories) {
//                    Log.i(TAG, "This are title ${titles.title}")
                    listTitles.add(titles.title)
                }
            }
            var distin = listTitles.distinct().toTypedArray()
            val distin2 = distin.toCollection(ArrayList())


            Log.i(TAG, "This are titlesize ${distin2.size}")
            for (title in distin) {
                Log.i(TAG, "This are title in list ${title}")
            }
            var arrTitles = ArrayList<CharSequence>()
            arrTitles = distin2

            Log.i(TAG, "This is distin2 ${arrTitles}")

            setFragmentResult("dataFromHome", bundleOf("list" to distin))

            mMap.moveCamera(CameraUpdateFactory.newLatLng(randRestLocation))
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        //return view;
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        //displayFilteredRestaurants()
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
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                    //moves the camera when startup
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))

                    //Sets the zoom
                    mMap.setMaxZoomPreference(25f)
                    mMap.setMinZoomPreference(12f)

//                    //loads restaurants after userLocation is known
                    getRestaurants()
                } else {
                    print("No Previous Location")
                }
            }
    }

    private fun getLocationNoCamera() {
        checkPermission()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    userLocation = LatLng(location.latitude, location.longitude)
                    //adds the marker
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

                } else {
                    print("No Previous Location")
                }
            }
    }

    private fun checkPermission() {

    }

    //Network request. Range == Meters
    private fun getRestaurants() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.yelp.com/v3/").addConverterFactory(GsonConverterFactory.create()).build()
        val svcYelp = retrofit.create(SVCYelp::class.java)
        //10km radius
        svcYelp.searchRestaurants("Bearer $YELP_KEY", 10000, userLocation.longitude, userLocation.latitude).enqueue(object :
            Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                val body = response.body()
                if (body == null) {
                    Log.i(TAG, "Invalid Response Body from Yelp API")
                    return
                }
                restaurants.addAll(body.restaurants)
                displayFilteredRestaurants()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "Failed To Get Restaurants $t")
            }
        })
    }

    private fun getRandomFilteredRestaurantsList(listRestaurants: MutableList<YelpRestaurant>, filter: String?, range: Int) : YelpRestaurant {
//        val filter = settings.getString("category", "")
//        val range = settings.getInt("rangeSelector", 10) //meters 1000 meters = 1km
        restaurants.forEach { restaurant ->
            if (restaurant.distance < range.toInt() * 1000 && restaurant.is_closed == "false") {
                for (i in restaurant.categories) {
                    if (filter == null || i.title.contains(filter.toString(), ignoreCase = true)) {
                        listRestaurants.add(restaurant)
                    }
                }
            }
        }
        val randomIndex = Random.nextInt(listRestaurants.size);
        return listRestaurants[randomIndex]
    }
    //display filtered restaurants
    private fun displayFilteredRestaurants() {
        val filter = settings.getString("category", "")
        val range = settings.getInt("rangeSelector", 10) //meters 1000 meters = 1km
        restaurants.forEach { restaurant ->
            if (restaurant.distance < range.toInt() * 1000 && restaurant.is_closed == "false") {
                for (i in restaurant.categories) {
                    if(filter == null || i.title.contains(filter.toString(), ignoreCase = true)) {

                        val url = URL("${restaurant.image}")
//                        getImageAsync(url) { bmp ->
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(restaurant.coordinates.latitude, restaurant.coordinates.longitude))
                                    .title("${restaurant.name}\n")
                                    .snippet("${restaurant.phone}")
                                    .draggable(true)
                            )
                            mMap.setOnInfoWindowClickListener(this);
//                        }
                    }
                }
            }
        }
    }

    private fun displayFilteredRestaurantsButton(randR: YelpRestaurant, listRestaurants: MutableList<YelpRestaurant>, filter: String?, range: Int) {
//        var titlesList = mutableListOf<CharSequence>()
//        var filterCat : CharSequence?
//        val filterNum = settings.getString("categoryPicker", "")
//        if(filterNum != "0") {
//            for (names in listRestaurants) {
//                for (titles in names.categories) {
//                    titlesList.add(titles.title)
//                }
//            }
//            var distin = titlesList.distinct().toTypedArray()
//            val distin2 = distin.toCollection(ArrayList())
//            filterCat = distin2[filterNum?.toInt()!! - 1]
//        } else {
//            filterCat = null
//        }
        val filter = settings.getString("category", "")
        val range = settings.getInt("rangeSelector", 10) //meters 1000 meters = 1km
        listRestaurants.forEach { restaurant ->
                if (restaurant.distance < range.toInt() * 1000 && restaurant.is_closed == "false") {
                    for (i in restaurant.categories) {
                        if(filter == null || i.title.contains(filter.toString(), ignoreCase = true)) {
                            val url = URL("${restaurant.image}")
//                        getImageAsync(url) { bmp ->
                            if(randR != restaurant) {
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                restaurant.coordinates.latitude,
                                                restaurant.coordinates.longitude
                                            )
                                        )
                                        .title("${restaurant.name}\n")
                                        .snippet("${restaurant.phone}")
                                        .draggable(true)
                                )
                            } else {
                                val mark = mMap.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                restaurant.coordinates.latitude,
                                                restaurant.coordinates.longitude
                                            )
                                        )
                                        .title("${restaurant.name}\n")
                                        .snippet("${restaurant.phone}")
                                        .draggable(true)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                )
                                mark.showInfoWindow()
//                        }
                        }
                        mMap.setOnInfoWindowClickListener(this);
                    }
                }
            }
        }

    }

//    private fun toArrayList() {
//        restaurants.forEach{ restaurant ->
//            for (i in restaurant.categories) {
//                restaurantArray.add(i.title)
//            }
//        }
//    }

    private fun getImageAsync(url:URL, completionHandler: (Bitmap) -> Unit) {
        completionHandler(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item);
    }

    override fun onInfoWindowClick(p0: Marker) {
        val restaurant = findRestaurantByPhone(p0.snippet)
        if (restaurant != null) {
            this.findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeFragmentToInfoWindow(restaurant.name, restaurant.price, restaurant.image, restaurant.rating.toString(), restaurant.categories[0].title, restaurant.distance.toString(), restaurant.phone, restaurant.location.address)
            )
        }
    }
    fun findRestaurantByPhone(phone: String): YelpRestaurant? {
        restaurants.forEach { restaurant ->
            if(restaurant.phone == phone) {
                return restaurant
            }
        }
        return null
    }
}
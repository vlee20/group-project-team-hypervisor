package com.example.projexample.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.projexample.database.Restaurant
import com.example.projexample.database.RestaurantDao
import kotlinx.coroutines.launch

class RestaurantViewModel(
    val database: RestaurantDao, // Data access object for the Profile entity
    application: Application) : AndroidViewModel(application) {

    val restList = database.getAllRestaurants()

    /**
     * Inserts the Profile object into the database.
     */
    fun insert(restName: String, restPhone: String, restAddress: String, restDistance: Double,
    restCategory: String, restRating: Double) {
        // Launch coroutines in the viewModelScope so that the coroutines are automatically
        // canceled when the ViewModel is destroyed.
        viewModelScope.launch {
            // Create Profile object using data stored in the EditText views
            var rest = Restaurant()
            rest.name = restName
            rest.address = restAddress;
            rest.phone = restPhone
            rest.distance = restDistance
            rest.category = restCategory
            rest.rating = restRating
//            profile.name = name.value.toString()
//            profile.emailAddress = emailAddress.value.toString()

            // Insert data to the database using the insert coroutine.
            database.insert(rest)
        }

    }

    /**
     * Deletes all Profile entities in the database.
     */
    fun clear() {
        // Launch coroutines in the viewModelScope so that the coroutines are automatically
        // canceled when the ViewModel is destroyed.
        viewModelScope.launch {
            // Delete data from the database using the clear coroutine.
            database.clear()
        }
    }
}
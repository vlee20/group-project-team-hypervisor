package com.example.projexample

import android.app.Application
import androidx.lifecycle.*
import com.example.projexample.database.Restaurant
import com.example.projexample.database.RestaurantDao
import kotlinx.coroutines.launch

class RestaurantViewModel(
    val database: RestaurantDao, // Data access object for the Profile entity
    application: Application) : AndroidViewModel(application) {

    // Retrieves all Profile objects from the database
    // Represented as a LiveData<List<Profile>>
    val restList = database.getAllRestaurants()

    /**
     * Creates a LiveData<String> that contains information from all Profile objects.
     * The Transformations.map function takes a LiveData object, performs operations on the
     * object and returns a LiveData-wrapped object.
     */
    var profileString = Transformations.map(restList) {
            rests -> // Profile refer to the underlying data List<Profile>
        var result = ""
        // Retrieve each Profile object from the list
        for (profile in rests) {
            // Create a string using the Profile name and email.
            // The Profile string is appended to a longer string with all Profile.
            result += "${profile.name}\n"
        }
        // Returns the aggregated String that is wrapped by the map function in a LiveData object.
        result
    }

    /**
     * Inserts the Profile object into the database.
     */
    fun insert(restName: String) {
        // Launch coroutines in the viewModelScope so that the coroutines are automatically
        // canceled when the ViewModel is destroyed.
        viewModelScope.launch {
            // Create Profile object using data stored in the EditText views
            var rest = Restaurant()
            rest.name = restName
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
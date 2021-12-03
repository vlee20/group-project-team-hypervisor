package com.example.projexample

import com.example.projexample.model.Coordinates
import com.example.projexample.model.YelpCategory
import com.example.projexample.model.YelpLocation
import com.example.projexample.model.YelpRestaurant
import com.google.gson.annotations.SerializedName
import junit.framework.TestCase
import org.junit.Test

class HomeFragmentTest : TestCase() {

//    data class YelpRestaurant(
//        @SerializedName("name") val name: String,
//        @SerializedName("image_url") val image: String,
//        @SerializedName("is_closed") val is_closed: String,
//        @SerializedName("url") val url: String,
//        @SerializedName("review_count") val reviewCount: Int,
//        @SerializedName("rating") val rating: Double,
//        @SerializedName("coordinates") val coordinates: Coordinates,
//        @SerializedName("price") val price: String,
//        @SerializedName("phone") val phone: String,
//        @SerializedName("categories") val categories: List<YelpCategory>,
//        @SerializedName("location") val location: YelpLocation,
//        @SerializedName("distance") val distance: Double
    @Test
    fun testFindRestaurantByPhone() {
        //val restaurant: YelpRestaurant("name"; null; null; null; null; null; null; null; 17239937323; "Coffee & Tea"; "San Francisco"; 45.234324;)
        var restaurant: YelpRestaurant
        val phoneNumber = 18937326482

    }
}
package com.example.projexample.model

import com.google.gson.annotations.SerializedName

//Might need more data, lets see if maps autofills the business info
data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
)

data class YelpRestaurant(
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val image: String,
    @SerializedName("is_closed") val is_closed: String,
    @SerializedName("url") val url: String,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("coordinates") val coordinates: Coordinates,
    @SerializedName("price") val price: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("categories") val categories: List<YelpCategory>,
    @SerializedName("location") val location: YelpLocation,
    @SerializedName("distance") val distance: Double
)

data class Coordinates(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class YelpCategory(
    @SerializedName("title") val title: String
)

data class YelpLocation(
    @SerializedName("address1") val address: String
)
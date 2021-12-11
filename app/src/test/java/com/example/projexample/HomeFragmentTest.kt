package com.example.projexample

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.projexample.model.Coordinates
import com.example.projexample.model.YelpCategory
import com.example.projexample.model.YelpLocation
import com.example.projexample.model.YelpRestaurant
import junit.framework.TestCase
import org.junit.Test
import kotlin.random.Random


class HomeFragmentTest : TestCase() {
    val listExample = mutableListOf(YelpRestaurant(name="INDO Restaurant & Lounge", image="https://s3-media3.fl.yelpcdn.com/bphoto/y_J4bFgKwg9oK6w2bW_c1w/o.jpg", is_closed="false", url="https://www.yelp.com/biz/indo-restaurant-and-lounge-palo-alto-2?adjust_creative=-N4vnWrr0rLjQTY7SLLftg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=-N4vnWrr0rLjQTY7SLLftg", reviewCount=1105, rating=4.0, coordinates= Coordinates(latitude=37.42086, longitude=-122.13699), price="$$", phone="+16504947168", categories= listOf(YelpCategory(title="Indonesian"), YelpCategory(title="Lounges")), location= YelpLocation(address="3295 El Camino Real"), distance=4651.797232358836),
        YelpRestaurant(name="A Slice of New York", image="https://s3-media2.fl.yelpcdn.com/bphoto/L8MJ8qMH62FPRh1D5zY1aQ/o.jpg", is_closed="false", url="https://www.yelp.com/biz/a-slice-of-new-york-sunnyvale?adjust_creative=-N4vnWrr0rLjQTY7SLLftg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=-N4vnWrr0rLjQTY7SLLftg", reviewCount=1177, rating=4.5, coordinates=Coordinates(latitude=37.375198, longitude=-122.057972), price="$", phone="+16509386969", categories= listOf(YelpCategory(title="Pizza"), YelpCategory(title="Italian"), YelpCategory(title="Sandwiches")), location=YelpLocation(address="1253 W El Camino Real"), distance=5695.56525553513))

    //tests to see if the random is implemented and so far the tests work with the listExample of the 2 restaurants
    @Test
    fun testgetRandomFilteredRestaurantsList() {
        val result = HomeFragment().getRandomFilteredRestaurantsList(listExample)
        if (result == listExample[0]) {
            assertEquals(result, listExample[0])
        } else {
            assertEquals(result, listExample[1])
        }
    }
    //gets the restaurants that has categories
    @Test
    fun testGetRestuarantCategories() {
        val result = HomeFragment().getRestuarantCategories(listExample)
        assertEquals(result, listExample)
    }
    //checks if the result is the same as the input of the restaurants
    fun testCheckSameRestuarants() {
        val result = HomeFragment().getRestuarantCategories(listExample)
        assertSame(result, listExample)
    }
    //gets the titles of the categories in the restaurants
    @Test
    fun testGetCategoriesTitles() {
        val result = HomeFragment().getCategoriesTitles(listExample)
        assertEquals(result, mutableListOf("Indonesian", "Lounges", "Pizza", "Italian", "Sandwiches"))
    }
    //gets the list of titles of the category and displays the restaurants that correspond to that title
    @Test
    fun testgetFilteredRestaurantCategories() {
        val result = HomeFragment().getFilteredRestaurantCategories(listExample, mutableListOf("None", "Indonesian", "Lounges", "Pizza", "Italian", "Sandwiches"))
        assertEquals(result, listExample)
    }
    //depending on the list of the restaurants it is passing through, it will display null
    @Test
    fun testFindRestaurantByPhone() {
        val result = HomeFragment().findRestaurantByPhone("+16504947168")
        assertNull(result)
    }

}
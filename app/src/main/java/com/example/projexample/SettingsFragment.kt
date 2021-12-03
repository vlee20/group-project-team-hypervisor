package com.example.projexample

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceManager
import com.example.projexample.model.YelpRestaurant
import android.app.Activity



class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var settings: SharedPreferences
    private val restaurants = mutableListOf<YelpRestaurant>()
    private var listRestaurants = mutableListOf<String>()
    var counter: Int = 0
    private var listEntries = mutableListOf<String>()
    val entries = arrayOf<CharSequence>("One", "Two", "Three")
    lateinit var cs: Array<CharSequence>
    lateinit var csValues: Array<CharSequence>
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        settings = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
        val lp = findPreference("categoryPicker") as ListPreference?
        val filter = settings.getString("category", "")
        val range = settings.getInt("rangeSelector", 10) //meters 1000 meters = 1km
        restaurants.forEach { restaurant ->
            if (restaurant.distance < range.toInt() * 1000 && restaurant.is_closed == "false") {
                for (i in restaurant.categories) {
                    if (filter == null || i.title.contains(filter.toString(), ignoreCase = true)) {
//                        listRestaurants.add(restaurant.categories.toString())
                        cs[counter] = (counter).toString()
                        csValues[counter] = (counter + 1).toString()
                        counter += 1
                    }
                }
            }
        }
        // trying to add to the list
//        if (lp != null) {
//            lp.entries = cs
//        }
//        if (lp != null) {
//            lp.entryValues = csValues
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings) {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }
        return super.onOptionsItemSelected(item);
    }
}
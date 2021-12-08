package com.example.projexample

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.setFragmentResultListener
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.projexample.model.YelpRestaurant
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var settings: SharedPreferences
    private var listRestaurants = mutableListOf<YelpRestaurant>()
    var counter: Int = 0
    private var listEntries = mutableListOf<String>()
    private var listEntriesValues = mutableListOf<String>()
    val entries = arrayOf<CharSequence>()
    var entryValues = arrayOf<CharSequence>()
    var ent = arrayOf<CharSequence?>("None")
    var entVal = arrayOf<CharSequence?>("1")
    private val TAG = "SettingsFragment"


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        settings = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
        val lp = findPreference("categoryPicker") as ListPreference?
        counter = 2
//        val bundle = this.arguments?.getCharSequenceArray("list")
//        val bundle = this.arguments
////        parentFragmentManager.getFragment(bundle, "list")
//        bundle?.getCharSequenceArray("list")
////        parentFragmentManager.setFragmentResultListener("dataFromHome", this)
//
//        Log.i(TAG, "This is bundle in settingsFrag ${bundle}")
        setFragmentResultListener("dataFromHome") { requestKey, bundle ->
            var listfromBundle = bundle.getCharSequenceArray("list")
            if (listfromBundle != null) {
                Log.i(TAG, "This is bundle in settingsFrag ${listfromBundle.size}")
            }
            val distin2 = listfromBundle?.toCollection(ArrayList())
            Log.i(TAG, "This is bundle in settingsFrag ${distin2}")

            var i: Int = 0
            if (listfromBundle != null) {
                while (i < listfromBundle.size) {
                    ent = append(ent, "${distin2?.get(i)}")
                    entVal = append(entVal, (counter.toString()))
                    counter += 1
                    i += 1
                }
            }
            if (lp != null) {
                lp.entries = ent
            }
            if (lp != null) {
                lp.entryValues = entVal
            }

        }

    }


    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
}
package com.example.projexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projexample.database.RestaurantDatabase
import com.example.projexample.databinding.FragmentFavoritesBinding
import com.example.projexample.viewmodel.RestaurantViewModel
import com.example.projexample.viewmodel.RestaurantViewModelFactory

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentFavoritesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        // Get reference to the application
        val application = requireNotNull(this.activity).application

        // Retrieve Intersection data access object.
        val dataSource = RestaurantDatabase.getInstance(application).restDao

        // Create a factory that generates IntersectionViewModels connected to the database.
        val viewModelFactory = RestaurantViewModelFactory(dataSource, application)

        // Generate an IntersectionViewModel using the factory.
        val restViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(RestaurantViewModel::class.java)

        // Connect the IntersectionViewModel with the variable in the layout
        binding.restViewModel2 = restViewModel
        // Assign the lifecycle owner to the activity so it manages the data accordingly.
        binding.lifecycleOwner = this

        var restAdap = RestListAdapter()
        // Attach intersection adapter.
        binding.intersectionRecyclerview.adapter = restAdap

        // Submit an updated list to the intersection listAdapter whenever it changes. Take note
        // intersectionList is a LiveData object.
        restViewModel.restList.observe(viewLifecycleOwner, Observer {
            it?.let {
                restAdap.submitList(it)
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }

}
package com.example.projexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projexample.database.RestaurantDatabase
import com.example.projexample.databinding.FragmentInfoWindowBinding
import com.example.projexample.viewmodel.RestaurantViewModel
import com.example.projexample.viewmodel.RestaurantViewModelFactory


class InfoWindow : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve arguments passed from the RecyclerView
        val args = InfoWindowArgs.fromBundle(
            requireArguments()
        )


        // Create data binding
        val binding: FragmentInfoWindowBinding = FragmentInfoWindowBinding.inflate(layoutInflater)
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
        binding.restViewModel = restViewModel
        // Assign the lifecycle owner to the activity so it manages the data accordingly.
        binding.lifecycleOwner = this

        binding.address.setText(args.address)
        binding.phone.setText(args.phone)
        binding.distance.setText(args.distance)
        binding.category.setText(args.category)
        binding.rating.setText(args.rating)

        Glide.with(this)
            .load(args.imageUrl)
            .into(binding.imageView)
        binding.price.setText(args.price)

        binding.name.setText(args.name)

        binding.favoriteButton.setOnClickListener{
            binding.restViewModel?.insert(binding.name.text.toString(), binding.address.text.toString(),
                binding.phone.text.toString(), binding.distance.text.toString().toDouble(), binding.category.text.toString(),
                binding.rating.text.toString().toDouble())
        }
        //return view;
        return binding.root

    }
}
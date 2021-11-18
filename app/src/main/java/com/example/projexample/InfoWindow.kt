package com.example.projexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.projexample.databinding.FragmentInfoWindowBinding


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

        //return view;
        return binding.root

    }
}
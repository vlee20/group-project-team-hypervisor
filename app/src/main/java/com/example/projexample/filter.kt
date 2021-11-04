package com.example.projexample

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projexample.databinding.FragmentFilterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [filter.newInstance] factory method to
 * create an instance of this fragment.
 */
public class filter : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFilterBinding.inflate(layoutInflater)

        binding.buttonFilter.setOnClickListener { view: View ->

//            //trying to use the bundle library (setting a key)
//            bundle.putString("key1", binding.categoryText.text.toString())
//            //trying to use the bundle library
//            fragment.arguments = bundle
//            //adding to class property but doesnt seem to work when sending to a different fragment
//            category_input = binding.categoryText.text.toString()
            val toast = Toast.makeText(
                activity,
                "Filtered ${binding.categoryText.text}",
                Toast.LENGTH_SHORT
            )
            toast.show()
            val bundle = bundleOf("category" to binding.categoryText.text.toString(), "range" to binding.continuousSlider.value.toDouble())
            view.findNavController().navigate(R.id.homeFragment, bundle)
//            view.findNavController()
//                .navigate(filterDirections.actionFilterToHomeFragment(binding.categoryText.text.toString(), start))
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment filter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            filter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
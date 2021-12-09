package com.example.projexample
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projexample.database.Restaurant
import com.example.projexample.databinding.RestItemBinding

class RestListAdapter() : ListAdapter<Restaurant,
        RestListAdapter.ItemViewHolder>(RestDiffCallback()) {
    /**
     * Inner class used to store data about each element in the RecyclerView. We provide a binding
     * to make it easy to access elements of the layout.
     */
    class ItemViewHolder(val binding: RestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Assign an intersection object and clickListener to the ItemViewHolder
         */
        fun bind(item: Restaurant) {
            binding.rest = item
        }
    }

    /**
     * Creates a View to visualize one element in the RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // We use an inflater based on the parent (IntersectionListFragment) and create an
        // ItemViewHolder with binding to the layout to simplify access.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RestItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * The function is called whenever the RecyclerView displays a specific element. It provides
     * access to the ItemViewHolder and its position.
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Assign the corresponding element from the data and the click listener. Take note getItem
        // is a function provided by ListAdapter.
        holder.bind(getItem(position))
    }
}

/**
 * Manages a RecyclerView list using the Eugene W. Myers's difference algorithm to reduce processing.
 */
class RestDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
    /**
     * We use intersectionId because it is a unique ID referring to a single element.
     */
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.restId == newItem.restId
    }

    /**
     * We check all properties to check equality between Intersection objects.
     */
    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.name == newItem.name
    }
}

/**
 * Listener that accepts a lambda function clickListener. It will be called when an element of the
 * RecyclerView is clicked/tapped.
 */
//class RestListener(val clickListener: (intersectionId: Long) -> Unit) {
//    fun onClick(intersection: Restaurant) = clickListener(intersection.restId)
//}
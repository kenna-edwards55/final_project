
package com.example.final_project

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RestaurantVerticalScrollItemBinding

/**
 * An adapter for displaying a list of orders in a RecyclerView.
 *
 * @param clickListener A lambda function to handle item click events.
 * @param deleteClickListener A lambda function to handle item delete click events.
 */
class RestaurantNameVerticalAdapter(val clickListener: (restaurant: Restaurant) -> Unit)
    : ListAdapter<Restaurant, RestaurantNameVerticalAdapter.RestaurantNameViewHolder>(RestaurantDiffItemCallback()) {

    /**
     * Creates and returns a new RestaurantNameViewHolder for the RecyclerView.
     *
     * @param parent The parent ViewGroup in which the ViewHolder will be created.
     * @param viewType The type of the view.
     * @return A new RestaurantNameViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RestaurantNameViewHolder = RestaurantNameViewHolder.inflateFrom(parent)

    /**
     * Binds data to the ViewHolder at the specified position in the RecyclerView.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: RestaurantNameViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    /**
     * ViewHolder class for individual order items in the RecyclerView.
     *
     * @param binding The data binding for the layout.
     */
    /**
    //     * ViewHolder class for individual order items in the RecyclerView.
    //     *
    //     * @param binding The data binding for the layout.
    //     */
    class RestaurantNameViewHolder(val binding: RestaurantVerticalScrollItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
             * Inflates a RestaurantNameViewHolder from the provided parent ViewGroup.
             *
             * @param parent The parent ViewGroup for inflating the ViewHolder.
             * @return A new RestaurantNameViewHolder.
             */
            fun inflateFrom(parent: ViewGroup): RestaurantNameViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantVerticalScrollItemBinding.inflate(layoutInflater, parent, false)
                return RestaurantNameViewHolder(binding)
            }
        }

        /**
         * Binds an order item to the ViewHolder.
         *
         * @param item The Order object to bind to the ViewHolder.
         */
        fun bind(item: Restaurant, clickListener: (restaurant: Restaurant) -> Unit) {
            binding.restaurant = item
            binding.restaurantName.text = item.restaurantName
            binding.root.setOnClickListener { clickListener(item) }

            Log.d("HorizontalAdapter", "${item.restaurantName}")

            //TODO use the click listener to navigate to the next screen

        }
    }
}

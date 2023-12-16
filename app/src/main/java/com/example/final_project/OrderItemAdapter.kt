
package com.example.final_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.OrderItemBinding

/**
 * An adapter for displaying a list of orders in a RecyclerView.
 *
 * @param clickListener A lambda function to handle item click events.
 * @param deleteClickListener A lambda function to handle item delete click events.
 */
class OrderItemAdapter()
    : ListAdapter<Order, OrderItemAdapter.OrderItemViewHolder>(OrderDiffItemCallback()) {

    /**
     * Creates and returns a new OrderItemViewHolder for the RecyclerView.
     *
     * @param parent The parent ViewGroup in which the ViewHolder will be created.
     * @param viewType The type of the view.
     * @return A new OrderItemViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : OrderItemViewHolder = OrderItemViewHolder.inflateFrom(parent)

    /**
     * Binds data to the ViewHolder at the specified position in the RecyclerView.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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
    class OrderItemViewHolder(val binding: OrderItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
            * Inflates a OrderItemViewHolder from the provided parent ViewGroup.
            *
            * @param parent The parent ViewGroup for inflating the ViewHolder.
            * @return A new OrderItemViewHolder.
            */
            fun inflateFrom(parent: ViewGroup): OrderItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OrderItemBinding.inflate(layoutInflater, parent, false)
                return OrderItemViewHolder(binding)
            }
        }

        /**
         * Binds an order item to the ViewHolder.
         *
         * @param item The Order object to bind to the ViewHolder.
         */
        fun bind(item: Order) {
            binding.order = item

            // Create and set up the inner RecyclerView for menu items
            val menuItemAdapter = MenuItemAdapter {}

            binding.menuItemsList.apply {
                adapter = menuItemAdapter
                layoutManager = LinearLayoutManager(context) // Set the layout manager
            }

            menuItemAdapter.submitList(item.orderItems)
        }
    }
}

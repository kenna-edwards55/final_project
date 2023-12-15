//package com.example.final_project
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.final_project.databinding.MenuItemBinding
//
///**
// * An adapter for displaying a list of orders in a RecyclerView.
// *
// * @param clickListener A lambda function to handle item click events.
// * @param deleteClickListener A lambda function to handle item delete click events.
// */
//class MenuItemAdapter(val clickListener: (menuItems: List<MenuItem>) -> Unit)
//    : ListAdapter<MenuItem, MenuItemAdapter.MenuItemViewHolder>(MenuDiffItemCallback()) {
//
//    /**
//     * Creates and returns a new OrderItemViewHolder for the RecyclerView.
//     *
//     * @param parent The parent ViewGroup in which the ViewHolder will be created.
//     * @param viewType The type of the view.
//     * @return A new OrderItemViewHolder.
//     */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
//            : MenuItemViewHolder = MenuItemViewHolder.inflateFrom(parent)
//
//    /**
//     * Binds data to the ViewHolder at the specified position in the RecyclerView.
//     *
//     * @param holder The ViewHolder to bind data to.
//     * @param position The position of the item in the list.
//     */
//    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item, clickListener)
//    }
//
//    /**
//     * ViewHolder class for individual order items in the RecyclerView.
//     *
//     * @param binding The data binding for the layout.
//     */
//    class MenuItemViewHolder(val binding: MenuItemBinding)
//        : RecyclerView.ViewHolder(binding.root) {
//
//        companion object {
//            /**
//             * Inflates a OrderItemViewHolder from the provided parent ViewGroup.
//             *
//             * @param parent The parent ViewGroup for inflating the ViewHolder.
//             * @return A new OrderItemViewHolder.
//             */
//            fun inflateFrom(parent: ViewGroup): MenuItemViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = MenuItemBinding.inflate(layoutInflater, parent, false)
//                return MenuItemViewHolder(binding)
//            }
//        }
//
//        /**
//         * Binds a order item to the ViewHolder and sets click listeners for item and delete button.
//         *
//         * @param item The Order object to bind to the ViewHolder.
//         * @param clickListener A lambda function to handle item click events.
//         * @param deleteClickListener A lambda function to handle item delete click events.
//         */
//        fun bind(item: MenuItem, clickListener: (menuItems: List<MenuItem>) -> Unit) {
//            binding.menuItem = item
//            binding.root.setOnClickListener { clickListener(item) }
//        }
//    }
//}

package com.example.final_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.MenuItemBinding

/**
 * An adapter for displaying a list of menu items in a RecyclerView.
 *
 * @param clickListener A lambda function to handle item click events.
 */
class MenuItemAdapter(val clickListener: (menuItems: List<MenuItem>) -> Unit)
    : ListAdapter<MenuItem, MenuItemAdapter.MenuItemViewHolder>(MenuDiffItemCallback()) {

    /**
     * Creates and returns a new MenuItemViewHolder for the RecyclerView.
     *
     * @param parent The parent ViewGroup in which the ViewHolder will be created.
     * @param viewType The type of the view.
     * @return A new MenuItemViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MenuItemViewHolder = MenuItemViewHolder.inflateFrom(parent)

    /**
     * Binds data to the ViewHolder at the specified position in the RecyclerView.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    /**
     * ViewHolder class for individual menu items in the RecyclerView.
     *
     * @param binding The data binding for the layout.
     */
    class MenuItemViewHolder(val binding: MenuItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
             * Inflates a MenuItemViewHolder from the provided parent ViewGroup.
             *
             * @param parent The parent ViewGroup for inflating the ViewHolder.
             * @return A new MenuItemViewHolder.
             */
            fun inflateFrom(parent: ViewGroup): MenuItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MenuItemBinding.inflate(layoutInflater, parent, false)
                return MenuItemViewHolder(binding)
            }
        }

        /**
         * Binds a menu item to the ViewHolder.
         *
         * @param item The MenuItem object to bind to the ViewHolder.
         * @param clickListener A lambda function to handle item click events.
         */
        fun bind(item: MenuItem, clickListener: (menuItems: List<MenuItem>) -> Unit) {
            binding.menuItem = item
            binding.root.setOnClickListener { clickListener(listOf(item)) }
        }
    }
}

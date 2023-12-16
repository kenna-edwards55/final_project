package com.example.final_project
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.MenuItemBinding

class MenuItemAdapter(private val onPlusClickListener: ((MenuItem) -> Unit)? = null, private val onMinusClickListener: ((MenuItem) -> Unit)? = null) :
    ListAdapter<MenuItem, MenuItemAdapter.MenuItemViewHolder>(MenuItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MenuItemViewHolder = MenuItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.bind(menuItem, onPlusClickListener, onMinusClickListener)

    }

    class MenuItemViewHolder(val binding: MenuItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
             * Inflates a RestaurantNameViewHolder from the provided parent ViewGroup.
             *
             * @param parent The parent ViewGroup for inflating the ViewHolder.
             * @return A new RestaurantNameViewHolder.
             */
            fun inflateFrom(parent: ViewGroup): MenuItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MenuItemBinding.inflate(layoutInflater, parent, false)
                return MenuItemViewHolder(binding)
            }
        }

        /**
         * Binds an order item to the ViewHolder.
         *
         * @param item The Order object to bind to the ViewHolder.
         */
        fun bind(menuItem: MenuItem, onPlusClickListener: ((MenuItem) -> Unit)?, onMinusClickListener: ((MenuItem) -> Unit)?) {
            binding.menuItem = menuItem

            binding.plusButton.setOnClickListener {
                if (onPlusClickListener != null) {
                    onPlusClickListener(menuItem)
                }
            }

            binding.minusButton.setOnClickListener {
                if (onMinusClickListener != null) {
                    onMinusClickListener(menuItem)
                }
            }

        }
    }
    private class MenuItemDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}

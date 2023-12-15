package com.example.final_project
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.MenuItemBinding

class MenuItemAdapter(private val onPlusClickListener: ((MenuItem) -> Unit)? = null, private val onMinusClickListener: ((MenuItem) -> Unit)? = null) :
    ListAdapter<MenuItem, MenuItemAdapter.MenuItemViewHolder>(MenuItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.bind(menuItem)
    }

    inner class MenuItemViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            binding.menuItem = menuItem

            // Set up click listeners for plus and minus buttons only if they are provided
            onPlusClickListener?.let { clickListener ->
                binding.plusButton.setOnClickListener {
                    clickListener.invoke(menuItem)
                }
            }

            onMinusClickListener?.let { clickListener ->
                binding.minusButton.setOnClickListener {
                    clickListener.invoke(menuItem)
                }
            }

            // Execute pending bindings to update the UI immediately
            binding.executePendingBindings()
        }
    }

    // DiffUtil callback for efficient RecyclerView updates
    private class MenuItemDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}

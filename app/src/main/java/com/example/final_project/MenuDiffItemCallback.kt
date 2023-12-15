package com.example.final_project

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil.ItemCallback implementation for comparing Order objects in a RecyclerView.
 *
 * This class defines how to compare items for use in DiffUtil to efficiently update a RecyclerView's data.
 */
class MenuDiffItemCallback : DiffUtil.ItemCallback<MenuItem>() {
    /**
     * Checks if two Order objects represent the same item.
     *
     * @param oldItem The old Order object.
     * @param newItem The new Order object.
     * @return True if the items are the same, otherwise false.
     */
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem)
            = (oldItem.itemId == newItem.itemId)

    /**
     * Checks if the contents of two Order objects are the same.
     *
     * @param oldItem The old Order object.
     * @param newItem The new Order object.
     * @return True if the contents are the same, otherwise false.
     */
    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem) = (oldItem == newItem)
}
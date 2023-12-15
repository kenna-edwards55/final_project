package com.example.final_project

import androidx.recyclerview.widget.DiffUtil
import com.example.final_project.Image


/**
 * Callback class for calculating the difference between two lists of [Image] items.
 *
 * This class is used by the RecyclerView adapter to efficiently update the displayed list when changes occur.
 */
class ImageDiffItemCallback : DiffUtil.ItemCallback<Image>() {
    /**
     * Called to check whether two items represent the same object.
     *
     * @param oldItem The old item in the list.
     * @param newItem The new item in the list.
     * @return True if the items have the same identifier, false otherwise.
     */
    override fun areItemsTheSame(oldItem: Image, newItem: Image)
            = (oldItem.fileName == newItem.fileName)

    /**
     * Called to check whether two items have the same data.
     *
     * @param oldItem The old item in the list.
     * @param newItem The new item in the list.
     * @return True if the items have the same content, false otherwise.
     */
    override fun areContentsTheSame(oldItem: Image, newItem: Image) = (oldItem == newItem)
}
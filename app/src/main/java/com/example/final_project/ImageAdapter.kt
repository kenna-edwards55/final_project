package com.example.project9

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_project.databinding.RestaurantImageItemBinding
import com.example.final_project.Image
import com.example.final_project.ImageDiffItemCallback

/**
 * Adapter for displaying a list of [Image] items in a RecyclerView.
 *
 * @param context The context in which the adapter is used.
 * @param clickListener The click listener for handling item clicks.
 */
class ImageAdapter(val context: Context, val clickListener: (imageId: String) -> Unit) :
    ListAdapter<Image, ImageAdapter.ImageItemViewHolder>(ImageDiffItemCallback()) {
    /**
     * Creates a new [ImageItemViewHolder] instance when needed.
     *
     * @param parent The parent view group.
     * @param viewType The type of the view.
     * @return A new instance of [ImageItemViewHolder].
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder.inflateFrom(parent)

    /**
     * Binds the data to the [ImageItemViewHolder] at the specified position.
     *
     * @param holder The [ImageItemViewHolder] to bind data to.
     * @param position The position of the item in the adapter's data set.
     */
    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, context)
    }

    /**
     * ViewHolder class for displaying individual [Image] items in the RecyclerView.
     *
     * @param binding The ViewBinding object for the item layout.
     */

    class ImageItemViewHolder(val binding: RestaurantImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Companion object providing a factory method to create a [ImageItemViewHolder] from a parent view.
         */
        companion object {
            /**
             * Creates a new [ImageItemViewHolder] instance from a parent view.
             *
             * @param parent The parent view group.
             * @return A new instance of [ImageItemViewHolder].
             */
            fun inflateFrom(parent: ViewGroup): ImageItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantImageItemBinding.inflate(layoutInflater, parent, false)
                return ImageItemViewHolder(binding)
            }
        }

        /**
         * Binds data to the [ImageItemViewHolder].
         *
         * @param image The [Image] item to bind.
         * @param clickListener The click listener for handling item clicks.
         * @param context The context in which the adapter is used.
         */
        fun bind(image: Image, clickListener: (imageId: String) -> Unit, context: Context) {
            binding.image= image
            binding.root.setOnClickListener { clickListener(image.imageUrl) }
            Glide.with(context).load(image.imageUrl).into(binding.restaurantImage)
        }
    }
}

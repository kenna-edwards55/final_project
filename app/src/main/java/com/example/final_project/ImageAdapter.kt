//package com.example.final_project
//
//import android.content.Context
//import android.text.format.DateUtils
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.final_project.databinding.FragmentRestaurantScreenBinding
////import com.example.project9.model.Post
//
///**
// * Adapter for displaying a list of [Post] items in a RecyclerView.
// *
// * @param context The context in which the adapter is used.
// * @param clickListener The click listener for handling item clicks.
// */
//class ImageAdapter(val context: Context, val clickListener: (postId: String) -> Unit) :
//    ListAdapter<Post, PostsAdapter.PostItemViewHolder>(PostDiffItemCallback()) {
//    /**
//     * Creates a new [PostItemViewHolder] instance when needed.
//     *
//     * @param parent The parent view group.
//     * @param viewType The type of the view.
//     * @return A new instance of [PostItemViewHolder].
//     */
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder =
//        PostItemViewHolder.inflateFrom(parent)
//
//    /**
//     * Binds the data to the [PostItemViewHolder] at the specified position.
//     *
//     * @param holder The [PostItemViewHolder] to bind data to.
//     * @param position The position of the item in the adapter's data set.
//     */
//    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item, clickListener, context)
//    }
//
//    /**
//     * ViewHolder class for displaying individual [Post] items in the RecyclerView.
//     *
//     * @param binding The ViewBinding object for the item layout.
//     */
//
//    class PostItemViewHolder(val binding: PostItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        /**
//         * Companion object providing a factory method to create a [PostItemViewHolder] from a parent view.
//         */
//        companion object {
//            /**
//             * Creates a new [PostItemViewHolder] instance from a parent view.
//             *
//             * @param parent The parent view group.
//             * @return A new instance of [PostItemViewHolder].
//             */
//            fun inflateFrom(parent: ViewGroup): PostItemViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = PostItemBinding.inflate(layoutInflater, parent, false)
//                return PostItemViewHolder(binding)
//            }
//        }
//
//        /**
//         * Binds data to the [PostItemViewHolder].
//         *
//         * @param post The [Post] item to bind.
//         * @param clickListener The click listener for handling item clicks.
//         * @param context The context in which the adapter is used.
//         */
//        fun bind(post: Post, clickListener: (postId: String) -> Unit, context: Context) {
//            binding.post = post
//            val username = post.user?.email as String
//            binding.root.setOnClickListener { clickListener(post.imageUrl) }
//            Glide.with(context).load(post.imageUrl).into(binding.ivPost)
//            binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
//        }
//    }
//}


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageAdapter(private val context: Context, private val imageList: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String) {
            val storageReference = FirebaseStorage.getInstance().getReference(imageUrl)

            Glide.with(context)
                .using(FirebaseImageLoader())
                .load(storageReference)
                .into(itemView.imageView)
        }
    }
}

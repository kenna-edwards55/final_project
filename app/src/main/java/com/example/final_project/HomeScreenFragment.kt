package com.example.final_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentHomeScreenBinding
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HomeScreenFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val toolbar:Toolbar = binding.toolbar

        val toggle = ActionBarDrawerToggle(
            requireActivity(),  // Use requireActivity() to get the AppCompatActivity
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setupNavigation()
//        addTheRestaurants()

        setHasOptionsMenu(true) // Enable options menu handling

        /**
         * Function to handle click on a note.
         */
        fun favoriteClicked (order : Order) {
            Log.d("HomeScreen", "in restaurantClicked() : noteId = ${order.restaurant!!.restaurantName}")
            viewModel.onRestaurantClicked(order.restaurant!!)
        }

        fun restaurantClicked (restaurant: Restaurant) {
            Log.d("HomeScreen", "in restaurantClicked() : noteId = ${restaurant.restaurantName}")
            Log.d("Restaurant Screen", "${viewModel.restaurant.value?.menu}")
            viewModel.onRestaurantClicked(restaurant)

        }

        /**
         * Creates an adapter for the RecyclerView to handle note items.
         */
        val favoritesAdapter = RestaurantNameHorizontalAdapter(::favoriteClicked)
        binding.favoritesList.adapter = favoritesAdapter

        val allRestaurantsAdapter = RestaurantNameVerticalAdapter(::restaurantClicked)
        binding.allRestaurantsList.adapter = allRestaurantsAdapter

        // In your Fragment or ViewModel
        viewModel.orders.observe(viewLifecycleOwner) { favorites ->
            favoritesAdapter.submitList(favorites)
        }

        viewModel.restaurants.observe(viewLifecycleOwner) { allRestaurants ->
            allRestaurantsAdapter.submitList(allRestaurants)
        }

        /**
         * Navigate to the NoteScreenFragment when a note is clicked.
         */
        viewModel.navigateToOrder.observe(viewLifecycleOwner, Observer { orderId ->
            orderId?.let {
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRestaurantScreenFragment()
                this.findNavController().navigate(action)
                viewModel.onOrderNavigated()
            }
        })

        return view
    }


    private fun addTheRestaurants() {
        Log.d("HomeScreen", "trying to add the restaurants")

// Create a new restaurant instance
//        val newRestaurant = Restaurant(
//            restaurantName = "taste_of_india",
//            restaurantDisplayName = "Taste of India",
//            imageUrls = listOf("https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/taste_of_india%2Ftaste_of_india_food.jpeg?alt=media&token=d4072fea-95d1-4197-a6f3-bb3bb352e7f8",
//                "https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/taste_of_india%2Ftaste_of_india_inside.jpeg?alt=media&token=c794d9dc-8f7a-4d23-8633-6278212edec9",
//                "https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/taste_of_india%2Ftaste_of_india_outside.jpeg?alt=media&token=92190b0f-7f6b-4cf3-9a08-0c0c45df1ea7"),
//            restaurantAddress = "316 E Fourth St Bloomington, IN 47408 United States"
//        )

//        val order = Order(
//            orderRestaurantName = "mcdonalds",
//            orderItems = listOf(MenuItem(itemName = "Fries", itemCost = "$2", itemQuantity = "1"), MenuItem(itemName = "Oreo McFlurry", itemCost = "$3", itemQuantity = "1"), MenuItem(itemName = "Filet O Fish", itemCost = "$5", itemQuantity = "3")),
//            orderDeliveryAddress = "700 N Woodlawn Ave Bloomington, IN 47408 United States",
//            orderSpecialInstructions = "BBQ sauce on the side"
//        )

        val currentTimestamp: Long = System.currentTimeMillis()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedTimestamp = dateFormat.format(Date(currentTimestamp))

        Log.d("HomeScreen", "${formattedTimestamp}")


//        binding.viewModel?.addOrderToDatabase(order)

//        binding.viewModel?.addRestaurantToDatabase(newRestaurant)

    }

    private fun setupNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    //do nothing
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_recent_orders -> {
                    // Handle Recent Orders click
                    // Example: navigate to RecentOrdersFragment
                    this.findNavController().navigate(R.id.action_homeScreenFragment_to_recentOrdersFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_calendar_view -> {
                    // Handle Calendar View click
                    // Example: navigate to CalendarViewFragment
                    this.findNavController().navigate(R.id.action_homeScreenFragment_to_calendarScreenFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sign_out -> {
                    // Handle Calendar View click
                    // Example: navigate to CalendarViewFragment
                    /**
                     * Sets a click listener for the user sign-out button.
                     *
                     * When the user sign-out button is clicked, it updates the [viewModel.loggedIn] flag to false and
                     * initiates the sign-out process through [viewModel.signOut].
                     */
                    binding.viewModel!!.loggedIn.value = false
                    binding.viewModel!!.signOut()
                    this.findNavController().navigate(R.id.action_homeScreenFragment_to_signInFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}


//TODO fix getting the user's data
//        val navHeaderBinding = NavHeaderBinding.inflate(inflater, binding.navView, false)

// Access the views in nav_header.xml
//        navHeaderBinding.userNameTextView.text = viewModel.currentUserData.value!!.name
//        navHeaderBinding.userEmailTextView.text = viewModel.currentUserData.value!!.email

// Set the inflated nav_header.xml to the header of the NavigationView
//        binding.navView.addHeaderView(navHeaderBinding.root)
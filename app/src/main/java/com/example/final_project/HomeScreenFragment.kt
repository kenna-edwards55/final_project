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
import com.example.final_project.databinding.NavHeaderBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore


class HomeScreenFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
//    private lateinit var toolbar: Toolbar

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

        //TODO fix getting the user's data
//        val navHeaderBinding = NavHeaderBinding.inflate(inflater, binding.navView, false)

        // Access the views in nav_header.xml
//        navHeaderBinding.userNameTextView.text = viewModel.currentUserData.value!!.name
//        navHeaderBinding.userEmailTextView.text = viewModel.currentUserData.value!!.email

        // Set the inflated nav_header.xml to the header of the NavigationView
//        binding.navView.addHeaderView(navHeaderBinding.root)

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
        addTheRestaurants()

        setHasOptionsMenu(true) // Enable options menu handling


//        viewModel.currentUserData.observe(viewLifecycleOwner, Observer { user ->
//            navHeaderBinding.userNameTextView.text = viewModel.currentUserData.value?.name
//            navHeaderBinding.userEmailTextView.text = viewModel.currentUserData.value?.email
//            // Update UI with the new user data
//            // user.email and user.name can be accessed here
//        })



        return view
    }

    private fun addTheRestaurants() {
        Log.d("HomeScreen", "trying to add the restaurants")

// Create a new restaurant instance
        val newRestaurant = Restaurant(
            restaurantName = "chipotle",
            restaurantDisplayName = "Chipotle",
            imageUrls = listOf("https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/chipotle%2Fchipotle_food.jpeg?alt=media&token=897055dd-d085-4961-a9ec-f0a23d755cc0",
                "https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/chipotle%2Fchipotle_front.jpeg?alt=media&token=7196baba-6b54-400f-bb5f-00373ae5b5da", "" +
                        "https://firebasestorage.googleapis.com/v0/b/final-project-1310c.appspot.com/o/chipotle%2Fchipotle_inside.jpeg?alt=media&token=9f4b8da3-fadd-4f4f-b562-b1b7abf48f3c"),
            restaurantAddress = "420 E Kirkwood Ave Bloomington, IN  47408 United States"
        )

        binding.viewModel?.addRestaurantToDatabase(newRestaurant)
        binding.viewModel?.addOrder()


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
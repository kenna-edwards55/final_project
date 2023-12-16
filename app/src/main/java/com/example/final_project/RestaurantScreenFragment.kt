
package com.example.final_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentRestaurantScreenBinding
import com.google.android.material.navigation.NavigationView

/**
 * A fragment for displaying a list of orders.
 */
class RestaurantScreenFragment : Fragment()   {
    val TAG = "RecentOrdersFragment"

    /**
     * View binding for the RecentOrdersFragment
     */
    private var _binding: FragmentRestaurantScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    /**
     * Called to create and return the view hierarchy associated with this fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The created view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRestaurantScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



//        val orderImageCode = binding.viewModel.order.value?.orderImageCode // Replace with your actual orderImageCode
//        val filteredImageUrls = viewModel.images.f
//            .filter { it.contains(orderImageCode) }

        //TODO finish scrolling images
//        val imageAdapter = ImageAdapter(view.context,)
//        binding.recyclerView.adapter = imageAdapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val toolbar: Toolbar = binding.toolbar

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

        fun plusClicked(menuItem: MenuItem) {
            viewModel.onPlusButtonClicked(menuItem)
        }

        fun minusClicked(menuItem: MenuItem) {
            viewModel.onMinusButtonClicked(menuItem)
        }


        val orderAdapter = OrderItemAdapter()
        viewModel.orders.observe(viewLifecycleOwner) {item ->
            orderAdapter.submitList(item)
        }

        val menuAdapter = MenuItemAdapter(::plusClicked, ::minusClicked)
        binding.rvMenuItems.adapter = menuAdapter

        viewModel.restaurant.observe(viewLifecycleOwner) { item ->
            menuAdapter.submitList(item.menu)

            Log.d("Restaurant Screen", "${viewModel.restaurant.value?.menu}")
        }

        viewModel.menuItems.observe(viewLifecycleOwner) {item ->
            menuAdapter.submitList(item)
            Log.d("Restaurant Screen", "updating menu items")
        }


        binding.checkout.setOnClickListener { it ->
            it?.let {
                val action = RestaurantScreenFragmentDirections.actionRestaurantScreenFragmentToCheckoutScreenFragment()
                this.findNavController().navigate(action)
            }
        }

        /**
         * Function to handle click on a order.
         */
        fun menuItemClicked (menuItem: MenuItem) {
            Log.d(TAG, "in menuItemClicked() : menuItemID = ${menuItem.itemId}")
//            viewModel.onMenuItemClicked(menuItem)
        }

        /**
         * Navigate to the OrderScreenFragment when a order is clicked.
         */
        viewModel.navigateToOrder.observe(viewLifecycleOwner, Observer { orderId ->
            orderId?.let {
//                val action = OrdersFragmentDirections
//                    .actionOrdersFragmentToEditOrderFragment(orderId)
//                this.findNavController().navigate(action)
                viewModel.onOrderNavigated()
            }
        })

        return view
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    //do nothing
                    this.findNavController().navigate(R.id.action_restaurantScreenFragment_to_homeScreenFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_recent_orders -> {
                    // Handle Recent Orders click
                    // Example: navigate to RecentOrdersFragment
                    this.findNavController().navigate(R.id.action_restaurantScreenFragment_to_recentOrdersFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_calendar_view -> {
                    // Handle Calendar View click
                    // Example: navigate to CalendarViewFragment
                    this.findNavController().navigate(R.id.action_restaurantScreenFragment_to_calendarScreenFragment)
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
                    this.findNavController().navigate(R.id.action_restaurantScreenFragment_to_signInFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
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
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
import com.example.final_project.databinding.FragmentOrderScreenBinding
import com.google.android.material.navigation.NavigationView

/**
 * A fragment for displaying a list of orders.
 */
class OrderScreenFragment : Fragment()   {
    val TAG = "RecentOrdersFragment"

    /**
     * View binding for the RecentOrdersFragment
     */
    private var _binding: FragmentOrderScreenBinding? = null
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

        _binding = FragmentOrderScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


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

//        /**
//         * Function to handle click on a order.
//         */
//        fun orderClicked (order : Order) {
//            Log.d(TAG, "in orderClicked() : orderId = ${order.orderId}")
//            viewModel.onOrderClicked(order)
//        }

//        fun plusClicked(menuItem:menuItem)

        /**
         * Function to handle click on a order.
         */
        fun menuItemClicked (menuItem: MenuItem) {
            Log.d(TAG, "in menuItemClicked() : menuItemID = ${menuItem.itemId}")
//            viewModel.onMenuItemClicked(menuItem)
        }

        /**
         * Creates an adapter for the RecyclerView to handle order items.
         */
        val adapter = OrderItemAdapter()
        binding.ordersList.adapter = adapter

//        val menuItemAdapter= MenuItemAdapter()
//        binding.ordersList.m




        /**
         * Observes changes in the orders list and update the RecyclerView.
         */
//        viewModel.orders.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        viewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            orders?.let {
                // Sort the orders by timestamp in descending order
                val sortedOrders = it.sortedByDescending { order -> order.timestamp }

                // Get the first order (most recent) or null if the list is empty
                val mostRecentOrder = sortedOrders.firstOrNull()

                // Update the adapter with the most recent order
                mostRecentOrder?.let { recentOrder ->
                    val singleItemList = listOf(recentOrder)
                    adapter.submitList(singleItemList)
                }
            }
        })

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
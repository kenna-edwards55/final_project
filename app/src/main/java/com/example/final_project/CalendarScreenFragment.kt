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
import com.example.final_project.databinding.FragmentCalendarScreenBinding
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CalendarScreenFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private var _binding: FragmentCalendarScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarScreenBinding.inflate(inflater, container, false)
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

        setHasOptionsMenu(true) // Enable options menu handling

        return view
    }

    private fun setupNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    //do nothing
                    this.findNavController().navigate(R.id.action_calendarScreenFragment_to_homeScreenFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_recent_orders -> {
                    // Handle Recent Orders click
                    // Example: navigate to RecentOrdersFragment
                    this.findNavController().navigate(R.id.action_calendarScreenFragment_to_recentOrdersFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_calendar_view -> {

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
                    this.findNavController().navigate(R.id.action_calendarScreenFragment_to_signInFragment)
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

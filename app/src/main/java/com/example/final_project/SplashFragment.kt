package com.example.final_project

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * Similar to a loading screen.
 * Will navigate either immediately to the list of orders or the sign in screen, depending on user's auth status.
 */
class SplashFragment : Fragment() {
    val viewModel : OrdersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        /**
         * Checks the current user's authentication status and navigates accordingly after a delay.
         *
         * It retrieves the current user using [viewModel.getCurrentUser]. If a user is authenticated,
         * it initializes the database reference, sets the [viewModel.loggedIn] flag to true, and navigates to the orders screen.
         * If no user is authenticated, it sets the [viewModel.loggedIn] flag to false and navigates to the sign-in screen.
         *
         * @param viewModel The OrdersViewModel to interact with the user authentication and navigation.
         */

        val currentUser = viewModel.getCurrentUser()

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            viewModel.signOut()
            viewModel.loggedIn.value = false
            this.findNavController().navigate(R.id.action_splashFragment_to_signInFragment)

//            if (currentUser != null) {
//                viewModel.initializeTheDatabaseReference()
//                viewModel.loggedIn.value = true
//                this.findNavController().navigate(R.id.action_splashFragment_to_homeScreenFragment)
//            }
//            else {
//                viewModel.loggedIn.value = false
//                this.findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
//            }

        }, 2000)
    }


}
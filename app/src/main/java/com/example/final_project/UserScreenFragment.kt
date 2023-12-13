package com.example.final_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.final_project.databinding.FragmentUserScreenBinding
//import com.example.project7.databinding.FragmentUserScreenBinding

/**
 * A fragment for user-related actions, including sign-in, sign-up, and sign-out.
 */
class UserScreenFragment : Fragment() {

    val viewModel : OrdersViewModel by activityViewModels()

    private var _binding: FragmentUserScreenBinding? = null
    private val binding get() = _binding!!

    /**
     * Called to create and return the view hierarchy associated with this fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return [View] The created view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val TAG = "UserScreenFragment"
        _binding = FragmentUserScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Observes the [viewModel.loggedIn] LiveData and updates UI components accordingly.
         *
         * It listens to changes in the [shouldShowButton] flag. If [shouldShowButton] is true, it shows the sign-out button
         * while hiding the sign-in and sign-up buttons. If [shouldShowButton] is false, it hides the sign-out button
         * and shows the sign-in and sign-up buttons.
         */
        viewModel.loggedIn.observe(viewLifecycleOwner, Observer {shouldShowButton ->
            if(shouldShowButton) {
                binding.userSignOutButton.visibility = View.VISIBLE
                binding.userSignInButton.visibility = View.GONE
                binding.userSignUpButton.visibility = View.GONE
            } else {
                binding.userSignOutButton.visibility = View.GONE
                binding.userSignInButton.visibility = View.VISIBLE
                binding.userSignUpButton.visibility = View.VISIBLE
            }
        })

        /**
         * Sets a click listener for the user sign-in button.
         *
         * When the user sign-in button is clicked, it updates the [viewModel.loggedIn] flag to false and
         * navigates to the sign-in screen.
         */
        binding.userSignInButton.setOnClickListener {
            viewModel.loggedIn.value = false
//            this.findNavController().navigate(R.id.action_userScreenFragment_to_signInFragment)
        }

        /**
         * Sets a click listener for the user sign-up button.
         *
         * When the user sign-up button is clicked, it updates the [viewModel.loggedIn] flag to false and
         * navigates to the sign-up screen.
         */
        binding.userSignUpButton.setOnClickListener {
            viewModel.loggedIn.value = false
//            this.findNavController().navigate(R.id.action_userScreenFragment_to_signUpFragment)
        }

        /**
         * Sets a click listener for the user sign-out button.
         *
         * When the user sign-out button is clicked, it updates the [viewModel.loggedIn] flag to false and
         * initiates the sign-out process through [viewModel.signOut].
         */
        binding.userSignOutButton.setOnClickListener {
            viewModel.loggedIn.value = false
            viewModel.signOut()
        }
        return view
    }
}
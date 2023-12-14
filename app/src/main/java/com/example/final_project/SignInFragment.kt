package com.example.final_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.final_project.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    val TAG = "SignInFragment"
    private var _binding: FragmentSignInBinding? = null
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

        val TAG = "SignInFragment"
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        /**
         * Observes the navigation trigger to the list of orders and handles the navigation.
         *
         * When the [navigate] flag is set to true, it navigates to the list of orders (RecentOrdersFragment).
         * Additionally, it calls [OrdersViewModel.onNavigatedToList] to perform post-navigation actions.
         */
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                Log.d(TAG, "Navigating to the list of orders.")
//                view.findNavController()
//                    .navigate(R.id.action_signInFragment_to_ordersFragment)
                viewModel.onNavigatedToList()
            }
        })

        /**
         * Observes the navigation trigger to the sign-up screen and handles the navigation.
         *
         * When the [navigate] flag is set to true, it navigates to the sign-up screen (SignUpFragment).
         * Additionally, it calls [OrdersViewModel.onNavigatedToSignUp] to perform post-navigation actions.
         */
        viewModel.navigateToSignUp.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                Log.d(TAG, "Navigating to the sign up screen.")
//                view.findNavController()
//                    .navigate(R.id.action_signInFragment_to_signUpFragment)
                viewModel.onNavigatedToSignUp()
            }
        })

        /**
         * Observes error messages and displays them as toasts when they occur.
         *
         * When an [error] message is received, it is displayed as a short toast message in the current context (if not null).
         */
        viewModel.errorHappened.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}
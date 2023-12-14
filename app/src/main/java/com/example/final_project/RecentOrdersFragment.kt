package com.example.final_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
//import com.example.final_project.databinding.FragmentOrdersBinding
import com.example.final_project.databinding.FragmentRecentOrdersBinding

/**
 * A fragment for displaying a list of orders.
 */
class RecentOrdersFragment : Fragment()   {
    val TAG = "RecentOrdersFragment"

    /**
     * View binding for the RecentOrdersFragment
     */
    private var _binding: FragmentRecentOrdersBinding? = null
    private val binding get() = _binding!!

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

        _binding = FragmentRecentOrdersBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        val toolbar = binding.ordersToolbar
//        toolbar.inflateMenu(R.menu.menu_main_activity)

        /**
         * Function to handle click on a order.
         */
        fun orderClicked (order : Order) {
            Log.d(TAG, "in orderClicked() : orderId = ${order.orderId}")
            viewModel.onOrderClicked(order)
        }

        /**
         * Creates an adapter for the RecyclerView to handle order items.
         */
        val adapter = OrderItemAdapter(::orderClicked)
        binding.ordersList.adapter = adapter

        /**
         * Observes changes in the orders list and update the RecyclerView.
         */
        viewModel.orders.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
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

        /**
         * Listens for icons to be clicked in the toolbar.
         *  IF the add order icon is pressed, addOrder() is called
         *  ELSE IF the user icon is clicked, user is prompted to sign in/out/up
         */
//        toolbar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.action_add_order -> {
//                    Log.d(TAG, "Add order icon in toolbar clicked")
//
//                    viewModel.addOrder()
//                    true
//                }
//                R.id.action_user_from_orders -> {
//                    Log.d(TAG, "User icon clicked. Navigating to user screen")
//
//                    this.findNavController().navigate(R.id.action_ordersFragment_to_userScreenFragment)
//                    true
//                }
//                else -> false
//            }
//        }
        return view
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
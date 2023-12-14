package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.final_project.databinding.FragmentSignUpBinding
//import com.example.final_project.databinding.FragmentSignUpBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    val TAG = "SignInFragment"
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var profilePictureImageView: ImageView

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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : OrdersViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        profilePictureImageView = binding.profilePictureImageView
        profilePictureImageView.setOnClickListener {
            showImageUploadDialog()
        }




        /**
         * Observes the navigation trigger to the sign-in screen and handles the navigation.
         *
         * When the [navigate] flag is set to true, it navigates to the sign-up screen (SignInFragment).
         * Additionally, it calls [OrdersViewModel.onNavigatedToSignIn] to perform post-navigation actions.
         */
        viewModel.navigateToSignIn.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_signUpFragment_to_signInFragment)
                viewModel.onNavigatedToSignIn()
            }
        })

        // Inside your SignUpFragment or your ViewModel (whichever is appropriate)



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

    fun showImageUploadDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_image_upload, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        alertDialog.show()

        // Handle the buttons in the dialog
        dialogView.findViewById<View>(R.id.btnChooseFromGallery).setOnClickListener {
            openGallery()
            alertDialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.btnCamera).setOnClickListener {
            openCamera()
            alertDialog.dismiss()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY)
    }

    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(packageManager) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        }
    }


}
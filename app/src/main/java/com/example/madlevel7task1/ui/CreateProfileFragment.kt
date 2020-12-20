package com.example.madlevel7task1.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task1.R
import com.example.madlevel7task1.databinding.FragmentCreateProfileBinding
import com.example.madlevel7task1.vm.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_create_profile.*
import java.util.Observer

class CreateProfileFragment : Fragment() {
    private lateinit var binding: FragmentCreateProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    private var profileImageUri: Uri? = null

    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGallery.setOnClickListener { onGalleryClick() }
        btnConfirm.setOnClickListener { onConfirmClick() }
    }

    private fun onGalleryClick() {
        // Create an Intent with action as ACTION_PICK
        val galleryIntent = Intent(Intent.ACTION_PICK)

        // Sets the type as image/*. This ensures only components of type image are selected
        galleryIntent.type = "image/*"

        // Start the activity using the gallery intent
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun onConfirmClick() {
        viewModel.createProfile(
            binding.etFirstName.text.toString(),
            binding.etLastName.text.toString(),
            binding.etProfileDescription.text.toString(),
            profileImageUri.toString()
        )

        observeProfileCreation()

        findNavController().navigate(R.id.action_CreateProfileFragment_to_ProfileFragment)
    }

    private fun observeProfileCreation() {
        viewModel.createSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(activity, R.string.successfully_created_profile, Toast.LENGTH_LONG)
                .show()
            findNavController().popBackStack()
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            Toast.makeText(activity, errorText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data
                    binding.ivProfileImage.setImageURI(profileImageUri)
                }
            }
        }
    }
}
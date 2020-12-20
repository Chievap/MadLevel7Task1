package com.example.madlevel7task1.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.madlevel7task1.R
import com.example.madlevel7task1.databinding.FragmentProfileBinding
import androidx.lifecycle.Observer
import com.example.madlevel7task1.vm.ProfileViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfile()
    }

    private fun observeProfile() {
        // Get the profile, so that the LiveData profile object is set and we can observe it
        viewModel.getProfile()

        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            binding.tvName.text =
                getString(R.string.profile_name, profile.firstName, profile.lastName)
            binding.tvDescription.text = profile.description
            // imageUri will be "" by default, so we can safely do .imageUri!!.
            if (profile.imageUri!!.isNotEmpty()) {
                binding.ivProfileImage.setImageURI(Uri.parse(profile.imageUri))
            }
        })
    }
}
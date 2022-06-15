package com.example.trakbucks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.databinding.FragmentProfileScreenBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import com.example.trakbucks.data.User
import com.example.trakbucks.databinding.FragmentSettingsScreenBinding
/**
 * A simple [Fragment] subclass.
 * Use the [ProfileScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileScreen : Fragment() {

    private var _binding : FragmentProfileScreenBinding? = null
    private val binding get() = _binding!!

    private var uri: Uri = Uri.parse("android.resource://com.example.trakbucks/" + R.drawable.ic_baseline_person_24)

    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            (activity?.application as TransactionApplication).database
                .transactionDao()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
             uri= data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.profileImage.setImageURI(uri)
            //binding.profileImage.tag = uri.toString()
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        initialise()


        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileScreenFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateSettings()
    {
        findNavController().navigate(R.id.action_profileScreen_to_settingsFragment)
    }

    private fun initialise(){
        myTransactionViewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
            val imageUri= userDetails[0].profileImage
            binding.profileImage.setImageURI(Uri.parse(imageUri))
        }
    }

    fun saveChanges()
    {
        myTransactionViewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
            val id = userDetails[0].id
            var name = binding.name.editText?.text.toString()
            if(name.isEmpty())
                name =userDetails[0].name

            var imageUri = uri.toString()
            if(imageUri.equals("android.resource://com.example.trakbucks/" + R.drawable.ic_baseline_person_24))
                imageUri= userDetails[0].profileImage

            binding.profileImage.setImageURI(Uri.parse(imageUri))

            val income = userDetails[0].income
            val expenditure = userDetails[0].expenditure
            val total = userDetails[0].total
            val user = User(id, imageUri, name, income, expenditure, total)


            myTransactionViewModel.updateUser(user)
        }

        Toast.makeText(activity, "Changes in profile saved!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileScreen_to_dashboard)
    }

    fun addImage()
    {
//        Toast.makeText(activity, "Profile image updated", Toast.LENGTH_SHORT).show()
        ImagePicker.with(this)
            .galleryOnly()	//User can only select image from Gallery
            .cropSquare()	//Crop square image, its same as crop(1f, 1f)
            .start()


    }


}
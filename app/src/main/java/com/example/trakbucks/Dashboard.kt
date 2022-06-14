package com.example.trakbucks

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.databinding.FragmentDashboardBinding
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import com.example.trakbucks.data.User
import java.util.Observer

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            (activity?.application as TransactionApplication).database
                .transactionDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        myTransactionViewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
            binding.nameText.text = userDetails[0].name
            binding.profileImage.setImageURI(Uri.parse(userDetails[0].profileImage))
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.dashboardFragment = this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun navigate(){
        findNavController().navigate(R.id.action_dashboard_to_profileScreen)
    }

}
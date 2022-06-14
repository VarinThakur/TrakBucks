package com.example.trakbucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.databinding.FragmentDashboardBinding
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory

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
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater,R.layout.fragment_dashboard,container,false)
        _binding = fragmentBinding
        binding.viewModel = myTransactionViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashboardFragment = this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun navigate(){
        findNavController().navigate(R.id.action_dashboard_to_profileScreen)
    }
}
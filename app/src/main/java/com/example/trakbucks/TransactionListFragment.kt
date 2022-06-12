package com.example.trakbucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.databinding.FragmentTransactionListBinding
import com.example.trakbucks.data.TransactionViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [TransactionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionListFragment : Fragment() {
    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var myTransactionViewModel: TransactionViewModel

    private lateinit var tranRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTransactionListBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        val adapter = TransactionListAdapter()
        tranRecyclerView=binding.allTransactionsRecyclerView
        tranRecyclerView.layoutManager= LinearLayoutManager(context)
        tranRecyclerView.adapter=adapter

        myTransactionViewModel= ViewModelProvider(this).get(TransactionViewModel::class.java)

        myTransactionViewModel.allTransactions.observe(viewLifecycleOwner, Observer { transactionList->
            adapter.setData(transactionList)
        })

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.transactionListFragment= this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun navigate(){
        findNavController().navigate(R.id.action_transactionListFragment_to_addTransactionScreen)
    }

}
package com.example.trakbucks

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.databinding.FragmentTransactionListBinding
import com.example.trakbucks.data.TransactionViewModel
import com.google.android.material.snackbar.Snackbar

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

        val swipeGesture= object :SwipeGesture(this.requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT->{
                        val pos= viewHolder.bindingAdapterPosition
                        val currentTransaction = adapter.transactionList[pos]

                        deleteTransaction(currentTransaction)
                        adapter.notifyItemChanged(viewHolder.bindingAdapterPosition)
                    }
                }

            }
        }

        ItemTouchHelper(swipeGesture).apply {
            attachToRecyclerView(binding.allTransactionsRecyclerView)
        }

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

    private fun deleteTransaction(currentTransaction:Transaction){

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_->
            myTransactionViewModel.deleteTransaction(currentTransaction)
            Toast.makeText(requireContext(),
                "Deleted Transaction of ${currentTransaction.name} Successfully.",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _,_->}
        builder.setTitle("Delete ${currentTransaction.name}?")
        builder.setMessage("Are you sure you want to delete the transaction of ${currentTransaction.name}?")
        builder.create().show()

//        Snackbar.make(activity, "Transaction Deleted successfully", Snackbar.LENGTH_LONG).apply {
//            setAction("Undo") {
//                myTransactionViewModel.addTransaction(currentTransaction)
//            }
//        }

    }

}
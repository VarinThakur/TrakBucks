package com.example.trakbucks

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trakbucks.data.*
import com.example.trakbucks.databinding.FragmentTransactionListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionListFragment : Fragment() {
    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            (requireActivity().application as TransactionApplication).database
                .transactionDao()
        )
    }

    private lateinit var tranRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("frag","view created!")
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTransactionListBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        val adapter = TransactionListAdapter()
        tranRecyclerView=binding.allTransactionsRecyclerView
        tranRecyclerView.layoutManager= LinearLayoutManager(context)
        tranRecyclerView.adapter=adapter



        myTransactionViewModel.allTransactions.observe(viewLifecycleOwner, Observer { transactionList->
            adapter.setData(transactionList)
        })

        val swipeGesture= object :SwipeGesture(this.requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,direction: Int) {
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
        binding.transactionListFragment = this

    }

    override fun onDestroyView() {
        Log.d("frag_tranList","Fragment Destroyed!")
        super.onDestroyView()
        _binding=null
    }

    fun navigate(){
        findNavController().navigate(R.id.action_transactionListFragment_to_addTransactionScreen)
    }

    private fun deleteTransaction(currentTransaction: Transaction){

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_->

            myTransactionViewModel.deleteTransaction(currentTransaction)
//            val user = test(currentTransaction)
            Log.d("Tran_delete","Transaction Deleted!")
//            Log.d("tran",currentTransaction.amount)

            myTransactionViewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
                Log.d("tran_observer","Enter Observer!")
                val id= userDetails[0].id
                val name= userDetails[0].name
                val image = userDetails[0].profileImage

                var income = userDetails[0].income
                var expenditure = userDetails[0].expenditure
                var total = userDetails[0].total
                Log.d("tran1","$total")

                if(currentTransaction.type==2)
                {
                    expenditure-= currentTransaction.amount.toInt()
                }
                else if(currentTransaction.type==1)
                {
                    income-=currentTransaction.amount.toInt()
                }

                total-= currentTransaction.amount.toInt()
                Log.d("tran2","$total")

                val user= User(id,image,name, income, expenditure, total)
                myTransactionViewModel.updateUser(user)
            }

//            myTransactionViewModel.updateUser(user!!)
            Toast.makeText(requireContext(),
                "Deleted Transaction of ${currentTransaction.name} Successfully.",
                Toast.LENGTH_SHORT).show()
            myTransactionViewModel.userDetails.removeObservers(viewLifecycleOwner)
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

//    fun test(currentTransaction:Transaction) : User?{
//        var user : User? = null
//        myTransactionViewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
//            Log.d("tran_observer","Enter Observer!")
//            val id= userDetails[0].id
//            val name= userDetails[0].name
//            val image = userDetails[0].profileImage
//
//            var income = userDetails[0].income
//            var expenditure = userDetails[0].expenditure
//            var total = userDetails[0].total
//            Log.d("tran1","$total")
//
//            if(currentTransaction.type==2)
//            {
//                expenditure-= currentTransaction.amount.toInt()
//            }
//            else if(currentTransaction.type==1)
//            {
//                income-=currentTransaction.amount.toInt()
//            }
//
//            total-= currentTransaction.amount.toInt()
//            Log.d("tran2","$total")
//
//            user= User(id,image,name, income, expenditure, total)
//        }
//
//        return user
//    }

}
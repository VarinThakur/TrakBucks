package com.example.trakbucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.databinding.FragmentTransactionListBinding
import com.example.trakbucks.model.TransactionViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: TransactionViewModel by activityViewModels()

    private lateinit var tranRecyclerView: RecyclerView
    lateinit var ids: Array<Int>
    lateinit var profImageid: Array<Int>
    lateinit var names: Array<String>
    lateinit var amounts: Array<String>
    lateinit var dates: Array<String>
    lateinit var times: Array<String>
    lateinit var types: Array<Int>

    var sumAmounts: Int= 0
    var sumIncome: Int=0
    var sumExpenditure: Int=0

    lateinit var allTransactions: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }

        ids= arrayOf(1,2,3,4,5,6,7,8,9,10)

        profImageid = arrayOf(
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24
        )

        names = arrayOf(
            "Vikram Bisht",
            "Vikram Bisht",
            "Rajinder Pathak",
            "Vikram Bisht",
            "Lisa Kudrow",
            "Vikram Bisht",
            "Vikram Bisht",
            "Vikram Bisht",
            "Vikram Bisht",
            "Vikram Bisht",
        )

        amounts = arrayOf(
            "20000",
            "10000",
            "30000",
            "20000",
            "10000",
            "30000",
            "20000",
            "10000",
            "30000",
            "10000"
        )

        dates = arrayOf(
            "01 Jun 2022",
            "02 Jun 2022",
            "03 Jun 2022",
            "01 Jun 2022",
            "02 Jun 2022",
            "03 Jun 2022",
            "01 Jun 2022",
            "02 Jun 2022",
            "03 Jun 2022",
            "01 Jun 2022"
        )

        times = arrayOf(
            "07:53 PM",
            "08:23 AM",
            "10:00 AM",
            "07:53 PM",
            "08:23 AM",
            "10:00 AM",
            "07:53 PM",
            "08:23 AM",
            "10:00 AM",
            "09:00 AM"
        )

        types= arrayOf(
            1,2,1,2,1,2,1,2,1,2
        )

      allTransactions= arrayListOf<Transaction>()
        getData()



    }

    private fun getData() {
        for(i in profImageid.indices)
        {
            val transaction= Transaction(ids[i],profImageid[i],names[i],amounts[i],dates[i],times[i],types[i])
            allTransactions.add(transaction)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTransactionListBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.transactionListFragment= this
        tranRecyclerView=binding.allTransactionsRecyclerView
        tranRecyclerView.layoutManager= LinearLayoutManager(context)
        tranRecyclerView.adapter=TransactionListAdapter(allTransactions)
    }

    private fun sum(){
        for(i in amounts.indices)
        {
            sumAmounts += amounts[i].toInt()
            if(types[i]==1)
                sumIncome+= amounts[i].toInt()
            else if(types[i]==2)
                sumExpenditure+= amounts[i].toInt()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun navigate(){
        findNavController().navigate(R.id.action_transactionListFragment_to_addTransactionScreen)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TransactionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransactionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
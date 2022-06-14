package com.example.trakbucks

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.data.TransactionViewModel
import com.google.android.material.card.MaterialCardView
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.NumberFormat
import java.util.*


class TransactionListAdapter() :RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>()
{
    private lateinit var myTransactionViewModel: TransactionViewModel
    var transactionList = emptyList<Transaction>()
    lateinit var mContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.transaction,parent,false)
        return TransactionListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        val currentTransaction = transactionList[position]
        //holder.profileImage_tran.setImageResource(currentTransaction.personImage)
        holder.name_tran.text= currentTransaction.name
        holder.date_tran.text=currentTransaction.date
        holder.time_tran.text=currentTransaction.time
        holder.profileImage_tran.setImageURI(Uri.parse(currentTransaction.personImage))

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences( mContext/* Activity context */)
        val currency = sharedPreferences.getString("Currency", "INR")

        when(currency)
        {
            "INR"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","IN")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
            "EUR"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","IE")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
            "DOL"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","US")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
        }

        when(currentTransaction.type)
        {
            1-> holder.type_tran.text="Received From"
            2-> holder.type_tran.text="Credited To"
            else -> holder.type_tran.text="Invalid Transaction"
        }

        holder.row_tran.setOnClickListener {
            val action = TransactionListFragmentDirections.actionTransactionListFragmentToUpdateTransactionScreen(currentTransaction)
            holder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val profileImage_tran: CircularImageView = itemView.findViewById(R.id.profile_image_transaction)
        val name_tran: TextView= itemView.findViewById(R.id.name_transaction)
        val amount_tran: TextView= itemView.findViewById(R.id.amount_transaction)
        val date_tran: TextView= itemView.findViewById(R.id.date_transaction)
        val time_tran: TextView= itemView.findViewById(R.id.time_transaction)
        val type_tran: TextView= itemView.findViewById(R.id.type_transaction)
        val row_tran: MaterialCardView= itemView.findViewById(R.id.transaction_row)
    }

    fun setData(transactionList: List<Transaction>){
        this.transactionList = transactionList
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mContext = recyclerView.context
    }


}
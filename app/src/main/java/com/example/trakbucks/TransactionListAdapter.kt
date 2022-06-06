package com.example.trakbucks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionListAdapter(private val transactionList : ArrayList<Transaction>) :RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.transaction,parent,false)
        return TransactionListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        val currentTransaction = transactionList[position]
        holder.profileImage_tran.setImageResource(currentTransaction.personImage)
        holder.name_tran.text= currentTransaction.name
        holder.amount_tran.text=currentTransaction.amount
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val profileImage_tran: ImageButton = itemView.findViewById(R.id.profile_image_transaction)
        val name_tran: TextView= itemView.findViewById(R.id.name_transaction)
        val amount_tran: TextView= itemView.findViewById(R.id.amount_transaction)
    }


}
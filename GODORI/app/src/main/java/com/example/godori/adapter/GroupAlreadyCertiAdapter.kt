package com.example.godori.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.data.ResponseGroupAfterData

class GroupAlreadyCertiAdapter(val group: ResponseGroupAfterData?, val memberList: List<ResponseGroupAfterData.Data.Member>, val context: Context) :
    RecyclerView.Adapter<GroupAlreadyCertiAdapter.MyViewHolder>() {
    //
    //    // Provide a reference to the views for each data item
    //    // Complex data items may need more than one view per item, and
    //    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var name : TextView = itemView.findViewById(R.id.gr_tv_already_name)
        var count : TextView = itemView.findViewById(R.id.gr_tv_already_count)
        var totalCount : TextView = itemView.findViewById(R.id.gr_tv_already_total_count)
        var userimg :ImageView = itemView.findViewById(R.id.gr_iv_more_title)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GroupAlreadyCertiAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gr_cdv_already_certi, parent, false)

        return MyViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(memberList[position].user_name)
        holder.count.setText(memberList[position].week_count.toString())
        holder.totalCount.setText("/" + group!!.data.group_cycle.toString())
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return memberList.size
    }
}
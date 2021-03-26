package com.example.godori.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.makeramen.roundedimageview.RoundedImageView

class GroupMoreAdapter() :
    RecyclerView.Adapter<GroupMoreAdapter.MyViewHolder>() {
    //
    //    // Provide a reference to the views for each data item
    //    // Complex data items may need more than one view per item, and
    //    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var groupName : TextView = itemView.findViewById(R.id.gr_tv_more_groupName)
        public var groupPicture : RoundedImageView = itemView.findViewById(R.id.gr_iv_more_title)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GroupMoreAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gr_cdv_more_info, parent, false)

        return MyViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.groupName.text = "고도리"
        holder.groupPicture.setImageResource(R.drawable.gr_img_info_title)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }
}
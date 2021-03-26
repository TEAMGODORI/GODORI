package com.example.godori.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R

class MyInfoPictureAdapter() :
    RecyclerView.Adapter<MyInfoPictureAdapter.MyViewHolder>() {
    //
    //    // Provide a reference to the views for each data item
    //    // Complex data items may need more than one view per item, and
    //    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var picture : ImageButton = itemView.findViewById(R.id.my_ib_picture)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyInfoPictureAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_cdv_picture, parent, false)

        return MyViewHolder(cardView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.picture.setImageResource(R.drawable.gr_img_info_excersie)
    }


}
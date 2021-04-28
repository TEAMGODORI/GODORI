package com.example.godori.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.godori.R
import com.example.godori.data.ResponseMypage


class MyInfoPictureAdapter(
    var certiList: List<ResponseMypage.Data.Certi>?,
    val context: Context?
) :
    RecyclerView.Adapter<MyInfoPictureAdapter.MyViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var myPicture : ImageButton = itemView.findViewById(R.id.my_ib_picture)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyInfoPictureAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_cdv_picture, parent, false)

        return MyViewHolder(cardView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        var size : Int = 0
        if (certiList != null) {
            size = certiList!!.size
        }
        return size
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myimgUrl: String = certiList?.get(position)!!.image

        if (myimgUrl.isNotEmpty()) {
            Glide.with(holder.myPicture.context)
                .load(myimgUrl)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.myPicture)
        }
        if(itemClick != null)
        {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }
}
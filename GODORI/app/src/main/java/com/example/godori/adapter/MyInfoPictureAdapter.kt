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
import com.example.godori.R
import com.example.godori.data.ResponseMypage


class MyInfoPictureAdapter(
    var certiList: List<ResponseMypage.Data.Certi>?,
    val context: Context?
) :
    RecyclerView.Adapter<MyInfoPictureAdapter.MyViewHolder>() {
    //
    //    // Provide a reference to the views for each data item
    //    // Complex data items may need more than one view per item, and
    //    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var picture : ImageButton = itemView.findViewById(R.id.my_ib_picture)
        var mImageView: ImageButton? = null
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
//        val path = certiList?.get(position)
//        holder.picture = certiList?.get(position)?.image as ImageButton

//        val imageUrl: String = certiList.get(position).getImg_url()
//        Glide.with(holder.itemView.context).load(imageUrl).into(holder.mImageView)
//        certiList?.get(position)?.let { holder.mImageView?.setImageResource(it.image) }
//        holder.mImageView?.setImageResource(certiList?.get(position)?.image)

//        holder.mItem = certiList?.get(position)
//        holder.mImageView?.setImageURI(mValues.get(position).uri)
//        holder.mDateView.setText(mValues.get(position).date)
//
//        holder.mView.setOnClickListener(View.OnClickListener {
//            if (null != mListener) {
//                // Notify the active callbacks interface (the activity, if the
//                // fragment is attached to one) that an item has been selected.
//                mListener.onListFragmentInteraction(holder.mItem)
//            }
//        })

//        val item = certiList[position]
//        val listener = View.OnClickListener {it ->
//            Toast.makeText(it.context, "Clicked: ${item.id}", Toast.LENGTH_SHORT).show()
//        }
//        holder.apply {
//            bind(listener, item)
//            itemView.tag = item
//        }
    }
}
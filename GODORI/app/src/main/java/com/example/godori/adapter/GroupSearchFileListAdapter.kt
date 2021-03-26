package com.example.godori.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.activity.GroupSearchActivity

class GroupSearchFileListAdapter internal constructor(
    private val list: List<String>,
    private val listener: GroupSearchActivity
) : RecyclerView.Adapter<GroupSearchFileListAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.gr_tv_search_result)
        val iconImageView: ImageButton = itemView.findViewById(R.id.gr_btn_search_result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gr_cdv_search, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val string: String = list[position]
        holder.titleTextView.text = string
        holder.iconImageView.setImageResource(R.drawable.gr_img_search_more)

        holder.itemView.setOnClickListener {
            listener.onSportSelected(string)
        }
    }

    override fun getItemCount() = list.size

    interface SportsAdapterListener {
        fun onSportSelected(string: String?)
    }
}
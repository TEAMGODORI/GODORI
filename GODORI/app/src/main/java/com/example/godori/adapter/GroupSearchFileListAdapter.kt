package com.example.godori.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.activity.GroupSearchActivity

class GroupSearchFileListAdapter internal constructor(
    private var list: List<String>,
    private val listener: GroupSearchActivity
) : RecyclerView.Adapter<GroupSearchFileListAdapter.SearchViewHolder>(), Filterable {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.gr_tv_search_result)
        val iconImageView: ImageButton = itemView.findViewById(R.id.gr_btn_search_result)
        init {
            itemView.setOnClickListener {
            }
        }
    }

    var searchableList: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gr_cdv_search, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.titleTextView.text = searchableList[position]
    }

    override fun getItemCount() = searchableList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            //            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                searchableList.clear()
//                if (constraint.isNullOrBlank()) {
//                    searchableList.addAll(list)
//                } else {
//                    val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
//                    for (item in 0..list.size) {
//                        if (list[item].toLowerCase().contains(filterPattern)) {
//                            searchableList.add(list[item])
//                        }
//                    }
//                }
//                return filterResults.also {
//                    it.values = searchableList
//                }
//            }
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                Log.v("charString", charString)
                Log.v("list", list.toString())

                if (charString.isEmpty()) {
                    searchableList = list as ArrayList<String>
                    Log.v("empty", "들어온 글자가 없슘")
                } else {
                    val filteredList = ArrayList<String>()
                    for (name in list) {
                        if (name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(name)
                        }
                    }
                    searchableList = filteredList
                    Log.v("filteredList", searchableList.toString())
                }

                val filterResults = FilterResults()
                filterResults.values = searchableList
                Log.v("filter result", filterResults.values.toString())
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableList = results?.values as ArrayList<String>
                Log.v("result", results?.values.toString())
                notifyDataSetChanged()
            }
        }
    }
}
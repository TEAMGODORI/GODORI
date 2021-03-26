package com.example.godori.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.GroupInfoActivity
import com.example.godori.R
import com.example.godori.adapter.GroupSearchFileListAdapter
import java.util.*
import kotlin.collections.ArrayList

class GroupSearchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchAdapter: GroupSearchFileListAdapter
    private lateinit var editText: AppCompatEditText
    private lateinit var noSearchResultsFoundText: TextView
    private lateinit var sportsList: List<String>
    private lateinit var clearQueryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_search)

        recyclerView = findViewById(R.id.gr_rcv_search)
        editText = findViewById(R.id.gr_et_search)
        noSearchResultsFoundText = findViewById(R.id.no_search_results_found_text)
        clearQueryImageView = findViewById(R.id.gr_btn_search)

        val list: List<String> = listOf("고도리", "고도오리", "고동스", "고목나무", "박지니")
        attachAdapter(list)

        editText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().toLowerCase(Locale.getDefault())
            filterWithQuery(query)
            toggleImageButton(query)
        }

        clearQueryImageView.setOnClickListener {
            editText.setText("")
        }
    }

    private fun attachAdapter(list: List<String>) {
        searchAdapter = GroupSearchFileListAdapter(list, this)
        recyclerView.adapter = searchAdapter
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            val filteredList: List<String> = onFilterChanged(query)
            attachAdapter(filteredList)
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            attachAdapter(sportsList)
        }
    }

    private fun onFilterChanged(filterQuery: String): List<String> {
        val filteredList = ArrayList<String>()
        for (currentSport in sportsList) {
            if (filterQuery.toLowerCase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(currentSport)
            }
        }
        return filteredList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            // Do something with spokenText
            editText.setText(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun toggleRecyclerView(sportsList: List<String>) {
        if (sportsList.isEmpty()) {
            recyclerView.visibility = View.INVISIBLE
            noSearchResultsFoundText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noSearchResultsFoundText.visibility = View.INVISIBLE
        }
    }

    private fun toggleImageButton(query: String) {
        if (query.isNotEmpty()) {
            clearQueryImageView.visibility = View.VISIBLE
        } else if (query.isEmpty()) {
            clearQueryImageView.visibility = View.INVISIBLE
        }
    }

    fun onSportSelected(string: String?) {
        val intent = Intent(applicationContext, GroupInfoActivity::class.java)
        intent.putExtra("DETAIL_SPORTS_DATA", string)
        startActivity(intent)
    }

    companion object {
        const val SPEECH_REQUEST_CODE = 0
    }
}
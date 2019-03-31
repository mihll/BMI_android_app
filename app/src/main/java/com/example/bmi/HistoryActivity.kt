package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.MainActivity.Companion.KEY_HISTORY_LIST
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val sharedPref = SharedPreference(this)

        val entriesList = sharedPref.getValueList(KEY_HISTORY_LIST)
        entriesList.reverse()

        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = BmiEntriesAdapter(entriesList,this.resources)
    }
}

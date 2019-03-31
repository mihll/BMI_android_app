package com.example.bmi

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.Logic.BmiEntry

class BmiEntriesAdapter(val bmiEntries: MutableList<BmiEntry>, private val resources: Resources) : RecyclerView.Adapter<BmiEntriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.bmi_entry_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = bmiEntries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordBmiEntry = bmiEntries[position]
        val bmiCategory = BmiCategories.valueOfCategoryBmi(resources,recordBmiEntry.type)

        holder.bmiValue.text = recordBmiEntry.bmiValue
        holder.bmiValue.setTextColor(bmiCategory.getColor(resources))
        if(recordBmiEntry.unit){
            holder.mass.text = resources.getString(R.string.bmi_pound_short_end,recordBmiEntry.mass)
            holder.height.text = resources.getString(R.string.bmi_inch_short_end,recordBmiEntry.height)
        } else{
            holder.mass.text = resources.getString(R.string.bmi_kilogram_short_end,recordBmiEntry.mass)
            holder.height.text = resources.getString(R.string.bmi_centimeter_short_end,recordBmiEntry.height)
        }
        holder.date.text = recordBmiEntry.date
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bmiValue : TextView = itemView.findViewById(R.id.entryBmiValue)
        val mass : TextView = itemView.findViewById(R.id.entryMass)
        val height : TextView = itemView.findViewById(R.id.entryHeight)
        val date : TextView = itemView.findViewById(R.id.entryDate)
    }

}
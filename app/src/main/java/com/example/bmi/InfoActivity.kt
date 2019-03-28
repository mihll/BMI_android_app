package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bmiValue = intent.getStringExtra(MainActivity.KEY_BMI_VALUE)
        val bmiType = intent.getStringExtra(MainActivity.KEY_BMI_TYPE)
        BMIValueText.text = bmiValue
        enumValues<BmiCategories>().forEach {
            if(bmiType == it.getType(resources)){
                BMIDescText.text = it.getDescription(resources)
                BMIValueText.setTextColor(it.getColor(resources))
                return
            }
        }
    }

}

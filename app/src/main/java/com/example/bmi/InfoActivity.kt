package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bmiValue = intent.getStringExtra(getString(R.string.bmi_bmi_number_key))
        val bmiDesc = intent.getStringExtra(getString(R.string.bmi_bmi_description_key))
        BMIValueText.text = bmiValue
        when(bmiDesc){
            getString(R.string.bmi_underweight) -> BMIDescText.text = getString(R.string.bmi_info_description_underweight,bmiDesc)
            getString(R.string.bmi_normal) -> BMIDescText.text = getString(R.string.bmi_info_description_normal,bmiDesc)
            getString(R.string.bmi_overweight) -> BMIDescText.text = getString(R.string.bmi_info_description_overweight,bmiDesc)
            getString(R.string.bmi_obesity) -> BMIDescText.text = getString(R.string.bmi_info_description_obesity,bmiDesc)
            getString(R.string.bmi_morbidObesity) -> BMIDescText.text = getString(R.string.bmi_info_description_morbidObesity,bmiDesc)
        }
    }

}

package com.example.bmi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.bmi.Logic.BmiForKgCm
import kotlin.math.round
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.aboutMe -> {}
            R.id.changeUnits ->{}
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("BMI_NUMBER", BMIResultNumber.text.toString())
        outState?.putString("BMI_DESCRIPTION", BMIResultDescription.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("BMI_NUMBER") != "") {
                val savedBmiResult = savedInstanceState.getString("BMI_NUMBER")!!
                val savedBmiDescp = savedInstanceState.getString("BMI_DESCRIPTION")!!
                updateResult(savedBmiResult, savedBmiDescp)
            }
            invalidateOptionsMenu()
        }
    }

    private fun checkAndConvertMass(enteredMass : String) : Int{
        if(enteredMass == ""){
            massTextField.error = "Please enter mass"
            return -1
        } else{
            val massInt = enteredMass.toInt()
            if (massInt <= 35){
                massTextField.error = "Mass has to above 35 kg"
                return -1
            } else return massInt
        }
    }

    private fun checkAndConvertHeight(enteredHeight : String) : Int{
        if(enteredHeight == ""){
            heightTextField.error = "Please enter height"
            return -1
        }else {
            val heightInt = enteredHeight.toInt()
            if(heightInt <= 110){
                heightTextField.error = "Height has to above 110 cm"
                return -1
            } else return heightInt
        }
    }

    private fun updateResult(roundedBmi: String, bmiDescp: String){
        BMIResultNumber.text = roundedBmi
        BMIResultDescription.text = bmiDescp
        when (bmiDescp) {
            "Severely underweight" -> BMIResultNumber.setTextColor(Color.parseColor("#3366CC"))
            "Underweight" -> BMIResultNumber.setTextColor(Color.parseColor("#00A693"))
            "Normal" -> BMIResultNumber.setTextColor(Color.parseColor("#3AC43D"))
            "Overweight" -> BMIResultNumber.setTextColor(Color.parseColor("#E07C2A"))
            "Obese" -> BMIResultNumber.setTextColor(Color.parseColor("#B80000"))
        }
    }


    fun onButtonClick(v: View){
        val enteredMass = massTextField.text.toString()
        val enteredHeight = heightTextField.text.toString()
        val convertedMass = checkAndConvertMass(enteredMass)
        val convertedHeight = checkAndConvertHeight(enteredHeight)
        if(convertedMass != -1 && convertedHeight != -1) {
            val counter = BmiForKgCm(convertedMass, convertedHeight)
            val roundedBmi = round(counter.countBmi() * 100) / 100
            when{
                roundedBmi < 16.0 -> updateResult(roundedBmi.toString(),"Severely underweight")
                roundedBmi < 18.5 -> updateResult(roundedBmi.toString(),"Underweight")
                roundedBmi < 25.0 -> updateResult(roundedBmi.toString(),"Normal")
                roundedBmi < 30.0 -> updateResult(roundedBmi.toString(),"Overweight")
                else -> updateResult(roundedBmi.toString(),"Obese")
            }
        }
    }
}

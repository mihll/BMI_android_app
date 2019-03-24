package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bmi.Logic.BmiForKgCm
import kotlin.math.round
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


    fun onButtonClick(v: View){
        val enteredMass = massTextField.text.toString()
        val enteredHeight = heightTextField.text.toString()
        val convertedMass = checkAndConvertMass(enteredMass)
        val convertedHeight = checkAndConvertHeight(enteredHeight)
        if(convertedMass != -1 && convertedHeight != -1) {
            val counter = BmiForKgCm(convertedMass, convertedHeight)
            val roundedBmi = round(counter.countBmi() * 100) / 100
            BMIResultNumber.text = roundedBmi.toString()
            when{
                roundedBmi < 15.0 -> BMIResultDescription.text = "Very severely underweight"
                roundedBmi < 16.0 -> BMIResultDescription.text = "Severely underweight"
                roundedBmi < 18.5 -> BMIResultDescription.text = "Underweight"
                roundedBmi < 25.0 -> BMIResultDescription.text = "Normal"
                roundedBmi < 30.0 -> BMIResultDescription.text = "Overweight"
                roundedBmi < 35.0 -> BMIResultDescription.text = "Moderately obese"
                roundedBmi < 40.0 -> BMIResultDescription.text = "Severely obese"
                roundedBmi < 45.0 -> BMIResultDescription.text = "Very severely obese"
                roundedBmi < 50.0 -> BMIResultDescription.text = "Morbidly Obese"
                roundedBmi < 60.0 -> BMIResultDescription.text = "Super Obese"
                else -> BMIResultDescription.text = "Hyper Obese"
            }
        }
    }
}

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
            BMIResultText.text = roundedBmi.toString()
        }
    }
}

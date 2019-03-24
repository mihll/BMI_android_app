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

    fun checkMass(enteredMass : String) : Boolean{
        if(enteredMass == ""){
            massTextField.error = "Please enter mass"
            return false
        } else if (enteredMass.toInt() <= 35){
            massTextField.error = "Mass has to above 35 kg"
            return false
        } else return true
    }

    fun checkHeight(enteredHeight : String) : Boolean{
        if(enteredHeight == ""){
            heightTextField.error = "Please enter height"
            return false
        } else if (enteredHeight.toInt() <= 110){
            heightTextField.error = "Height has to above 110 cm"
            return false
        } else return true
    }


    fun onButtonClick(v: View){
        val enteredMass = massTextField.text.toString()
        val enteredHeight = heightTextField.text.toString()

        if(checkMass(enteredMass)){
            if (checkHeight(enteredHeight)){
                val counter = BmiForKgCm(enteredMass.toInt(), enteredHeight.toInt())
                var roundedBmi = round(counter.countBmi() * 100) / 100
                BMIResultText.text = roundedBmi.toString();
            }
        }else checkHeight(enteredHeight)
    }
}

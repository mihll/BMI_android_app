package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bmi.Logic.Bmi
import com.example.bmi.Logic.BmiForKgCm
import com.example.bmi.Logic.BmiForLbIn
import kotlin.math.round
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var currentUnit = false
    private var currentCounter : Bmi = BmiForKgCm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //////////////////////////////////////////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.aboutMe -> {
                startActivity(Intent(this, AboutMeActivity::class.java))
                return true
            }
            R.id.changeUnits -> {
                this.currentUnit = !currentUnit
                unitsChanged()
                clearTextViews()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.bmi_bmi_number_key), BMIResultNumber.text.toString())
        outState?.putString(getString(R.string.bmi_bmi_description_key), BMIResultDescription.text.toString())
        outState?.putBoolean(getString(R.string.bmi_current_unit_key), currentUnit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(getString(R.string.bmi_bmi_number_key)) != "") {
                val savedBmiResult = savedInstanceState.getString(getString(R.string.bmi_bmi_number_key))!!
                val savedBmiDescp = savedInstanceState.getString(getString(R.string.bmi_bmi_description_key))!!
                currentUnit = savedInstanceState.getBoolean(getString(R.string.bmi_current_unit_key))
                unitsChanged()
                updateResult(savedBmiResult, savedBmiDescp)
            }
            invalidateOptionsMenu()
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    private fun unitsChanged(){
        if(currentUnit){
            currentCounter = BmiForLbIn()
            massTextFieldHeader.text = getString(R.string.bmi_main_mass_lb)
            heightTextFieldHeader.text = getString(R.string.bmi_main_height_in)
        }else{
            currentCounter = BmiForKgCm()
            massTextFieldHeader.text = getString(R.string.bmi_main_mass_kg)
            heightTextFieldHeader.text = getString(R.string.bmi_main_height_cm)
        }
    }

    private fun clearTextViews(){
            infoButton.visibility = View.INVISIBLE
            massTextField.text = null
            massTextField.error = null
            heightTextField.text = null
            heightTextField.error = null
            BMIResultNumber.text = ""
            BMIResultDescription.text = ""
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private fun checkAndConvertMass(enteredMass : String) : Int{
        val massInt : Int
        if(enteredMass == ""){
            massTextField.error = getString(R.string.bmi_main_noEnteredMassError)
            return -1
        }else if (currentUnit){
            try{
                massInt = enteredMass.toInt()
            } catch (e : Exception){
                massTextField.error = getString(R.string.bmi_main_incorrectNumberError)
                return -1
            }
            if (massInt < 90 || massInt > 999){
                massTextField.error = getString(R.string.bmi_main_imperial_massNotInRangeError)
                return -1
            } else return massInt
        } else {
            try{
                massInt = enteredMass.toInt()
            } catch (e : Exception){
                massTextField.error = getString(R.string.bmi_main_incorrectNumberError)
                return -1
            }
            if (massInt < 40 || massInt > 600){
                massTextField.error = getString(R.string.bmi_main_metric_massNotInRangeError)
                return -1
            }
            return massInt
        }
    }

    private fun checkAndConvertHeight(enteredHeight : String) : Int {
        val heightInt: Int
        if (enteredHeight == "") {
            heightTextField.error = getString(R.string.bmi_main_noEnteredHeightError)
            return -1
        } else if (currentUnit) {
            try {
                heightInt = enteredHeight.toInt()
            } catch (e: Exception) {
                heightTextField.error = getString(R.string.bmi_main_incorrectNumberError)
                return -1
            }
            if (heightInt < 50 || heightInt > 100) {
                heightTextField.error = getString(R.string.bmi_main_imperial_heightNotInRangeError)
                return -1
            } else return heightInt
        } else {
            try {
                heightInt = enteredHeight.toInt()
            } catch (e: Exception) {
                heightTextField.error = getString(R.string.bmi_main_incorrectNumberError)
                return -1
            }
            if (heightInt < 130 || heightInt > 250) {
                heightTextField.error = getString(R.string.bmi_main_metric_heightNotInRangeError)
                return -1
            }
            return heightInt
        }
    }

    fun onCountButtonClick(v: View){
        val enteredMass = massTextField.text.toString()
        val enteredHeight = heightTextField.text.toString()
        val convertedMass = checkAndConvertMass(enteredMass)
        val convertedHeight = checkAndConvertHeight(enteredHeight)
        if(convertedMass != -1 && convertedHeight != -1) {
            val roundedBmi = round(currentCounter.countBmi(convertedMass,convertedHeight) * 100) / 100
            when{
                roundedBmi < 18.5 -> updateResult(roundedBmi.toString(),getString(R.string.bmi_underweight))
                roundedBmi < 25.0 -> updateResult(roundedBmi.toString(),getString(R.string.bmi_normal))
                roundedBmi < 30.0 -> updateResult(roundedBmi.toString(),getString(R.string.bmi_overweight))
                roundedBmi < 40.0 -> updateResult(roundedBmi.toString(),getString(R.string.bmi_obesity))
                else -> updateResult(roundedBmi.toString(),getString(R.string.bmi_morbidObesity))
            }
        }
    }


    private fun updateResult(roundedBmi: String, bmiDescp: String){
        BMIResultNumber.text = roundedBmi
        BMIResultDescription.text = bmiDescp
        infoButton.visibility = View.VISIBLE
        when (bmiDescp) {
            getString(R.string.bmi_underweight) -> {
                BMIResultNumber.setTextColor(ContextCompat.getColor(this,R.color.lapisLazuli))
                infoButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lapisLazuli)
            }
            getString(R.string.bmi_normal) -> {
                BMIResultNumber.setTextColor(ContextCompat.getColor(this,R.color.verdigris))
                infoButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.verdigris)
            }
            getString(R.string.bmi_overweight) -> {
                BMIResultNumber.setTextColor(ContextCompat.getColor(this,R.color.peridot))
                infoButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.peridot)
            }
            getString(R.string.bmi_obesity) -> {
                BMIResultNumber.setTextColor(ContextCompat.getColor(this,R.color.vividTangelo))
                infoButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.vividTangelo)
            }
            getString(R.string.bmi_morbidObesity) -> {
                BMIResultNumber.setTextColor(ContextCompat.getColor(this,R.color.pompeianRed))
                infoButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.pompeianRed)
            }
        }
    }

    fun onInfoButtonClick(v: View){
        val infoIntent = Intent(this, InfoActivity::class.java)
        infoIntent.putExtra(getString(R.string.bmi_bmi_number_key),BMIResultNumber.text.toString())
        infoIntent.putExtra(getString(R.string.bmi_bmi_description_key),BMIResultDescription.text.toString())
        startActivity(infoIntent)
    }
}

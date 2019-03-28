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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_BMI_VALUE = "BMI_VALUE"
        const val KEY_BMI_TYPE = "BMI_TYPE"
        const val KEY_CURRENT_UNITS= "CURRENT_UNITS"
    }

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
        outState?.putString(KEY_BMI_VALUE, BMIResultNumber.text.toString())
        outState?.putString(KEY_BMI_TYPE, BMIResultType.text.toString())
        outState?.putBoolean(KEY_CURRENT_UNITS, currentUnit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(KEY_BMI_VALUE) != ""){
                val savedBmiResult = savedInstanceState.getString(KEY_BMI_VALUE)!!
                val savedBmiDescription = savedInstanceState.getString(KEY_BMI_TYPE)!!
                updateResult(savedBmiResult.toDouble(), BmiCategories.valueOfCategoryBmi(resources,savedBmiDescription))
            }
            currentUnit = savedInstanceState.getBoolean(KEY_CURRENT_UNITS)
            unitsChanged()
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
            BMIResultType.text = ""
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private fun checkUserInput() : Boolean {
        var massCorrect = checkMassInput()
        var heightCorrect = checkHeightInput()
        if(massCorrect) massCorrect = checkMassRange()
        if (heightCorrect) heightCorrect = checkHeightRange()
        return massCorrect && heightCorrect
    }

    private fun checkMassInput() : Boolean {
        val enteredMass = massTextField.text.toString()
        if (enteredMass == "") {
            massTextField.error = getString(R.string.bmi_main_noEnteredMassError)
            return false
        }
        try {
            enteredMass.toInt()
        } catch (e: Exception) {
            massTextField.error = getString(R.string.bmi_main_incorrectNumberError)
            return false
        }
        return true
    }

    private fun checkMassRange() : Boolean {
        val massInRange = currentCounter.checkMassRange(massTextField.text.toString().toInt())
        if(!currentUnit){
            if(!massInRange){
                massTextField.error = getString(R.string.bmi_main_metric_massNotInRangeError,currentCounter.validMassRange.start,currentCounter.validMassRange.endInclusive)
                return false
            }
        }else{
            if(!massInRange){
                massTextField.error = getString(R.string.bmi_main_imperial_massNotInRangeError,currentCounter.validMassRange.start,currentCounter.validMassRange.endInclusive)
                return false
            }
        }
        return true
    }

    private fun checkHeightInput() : Boolean {
        val enteredHeight = heightTextField.text.toString()
        if (enteredHeight == "") {
            heightTextField.error = getString(R.string.bmi_main_noEnteredHeightError)
            return false
        }
        try {
            enteredHeight.toInt()
        } catch (e: Exception) {
            heightTextField.error = getString(R.string.bmi_main_incorrectNumberError)
            return false
        }
        return true
    }

    private fun checkHeightRange() : Boolean {
        val heightInRange = currentCounter.checkHeightRange(heightTextField.text.toString().toInt())
        if(!currentUnit){
            if(!heightInRange){
                heightTextField.error = getString(R.string.bmi_main_metric_heightNotInRangeError,currentCounter.validHeightRange.start,currentCounter.validHeightRange.endInclusive)
               return false
            }
        }else{
            if(!heightInRange){
                heightTextField.error = getString(R.string.bmi_main_imperial_heightNotInRangeError,currentCounter.validHeightRange.start,currentCounter.validHeightRange.endInclusive)
                return false
            }
        }
        return true
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    fun onCountButtonClick(v: View){
        if(checkUserInput()){
            val mass = massTextField.text.toString().toInt()
            val height = heightTextField.text.toString().toInt()
            val countedBmi = currentCounter.countBmi(mass,height)
            enumValues<BmiCategories>().forEach {
                if(countedBmi <= it.getUpperRange()){
                    updateResult(countedBmi,it)
                    return
                }
            }
        }
    }

    private fun updateResult(countedBmi: Double, category: BmiCategories){
        BMIResultNumber.text = String.format("%.${2}f",countedBmi)
        BMIResultType.text = category.getType(resources)
        infoButton.visibility = View.VISIBLE
        BMIResultNumber.setTextColor(category.getColor(resources))
        infoButton.backgroundTintList = ContextCompat.getColorStateList(this,category.getColorID())
    }

    fun onInfoButtonClick(v: View){
        val infoIntent = Intent(this, InfoActivity::class.java)
        infoIntent.putExtra(KEY_BMI_VALUE,BMIResultNumber.text.toString())
        infoIntent.putExtra(KEY_BMI_TYPE,BMIResultType.text.toString())
        startActivity(infoIntent)
    }
}

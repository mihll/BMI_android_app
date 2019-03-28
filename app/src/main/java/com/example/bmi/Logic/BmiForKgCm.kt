package com.example.bmi.Logic

class BmiForKgCm : Bmi {

    override val validMassRange = 40..600
    override val validHeightRange = 130..250

    override fun countBmi(mass: Int, height: Int): Double {
        return mass*10000.0 / (height*height)
    }
}
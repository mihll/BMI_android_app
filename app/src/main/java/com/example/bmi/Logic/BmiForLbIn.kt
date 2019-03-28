package com.example.bmi.Logic

class BmiForLbIn : Bmi {

    override val validMassRange = 90..999
    override val validHeightRange = 50..100

    override fun countBmi(mass: Int, height: Int): Double {
        return mass * 703.0 / (height*height)
    }
}
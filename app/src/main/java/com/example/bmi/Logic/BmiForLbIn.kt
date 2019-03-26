package com.example.bmi.Logic

class BmiForLbIn : Bmi {

    override fun countBmi(mass: Int, height: Int): Double {
        val bmi = mass * 703.0 / (height*height)
        return bmi
    }
}
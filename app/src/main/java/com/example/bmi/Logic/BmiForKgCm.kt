package com.example.bmi.Logic

class BmiForKgCm : Bmi {

    override fun countBmi(mass: Int, height: Int): Double {
        val bmi = mass*10000.0 / (height*height)
        return bmi
    }
}
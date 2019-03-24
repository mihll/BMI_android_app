package com.example.bmi.Logic

class BmiForKgCm(var mass : Int, var height : Int) : Bmi {

    override fun countBmi(): Double {
        val bmi = mass*10000.0 / (height*height)
        return bmi
    }
}
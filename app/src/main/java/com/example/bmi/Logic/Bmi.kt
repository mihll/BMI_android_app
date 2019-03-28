package com.example.bmi.Logic

interface Bmi {
    val validMassRange : IntRange
    val validHeightRange : IntRange

    fun countBmi(mass:Int, height: Int) : Double
    fun checkMassRange(mass: Int): Boolean {
        return mass in validMassRange
    }
    fun checkHeightRange(height: Int) : Boolean{
        return height in validHeightRange
    }
}
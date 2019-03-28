package com.example.bmi

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat

enum class BmiCategories(
    private val colorID: Int,
    private val typeID: Int,
    private val descriptionID : Int,
    private val lowerRange: Double,
    private val upperRange: Double
){
    UNDERWEIGHT(
        R.color.lapisLazuli,
        R.string.bmi_underweight,
        R.string.bmi_info_description_underweight,
        0.0,
        18.49
    ),
    NORMAL(
        R.color.verdigris,
        R.string.bmi_normal,
        R.string.bmi_info_description_normal,
        18.5,
        24.99
    ),
    OVERWEIGHT(
        R.color.peridot,
        R.string.bmi_overweight,
        R.string.bmi_info_description_overweight,
        25.0,
        29.99
    ),
    OBESE(
        R.color.vividTangelo,
        R.string.bmi_obesity,
        R.string.bmi_info_description_obese,
        30.0,
        34.99
    ),
    EXTREMELY_OBESE(
        R.color.pompeianRed,
        R.string.bmi_extremeObesity,
        R.string.bmi_info_description_extremelyObese,
        35.0,
        1000.0
    );

    fun getColor(resources: Resources) = ResourcesCompat.getColor(resources,colorID,null)

    fun getColorID() = colorID

    fun getType(resources: Resources) = resources.getString(typeID)

    fun getDescription(resources: Resources) = resources.getString(descriptionID,String.format("%.${2}f",lowerRange),String.format("%.${2}f",upperRange))

    fun getUpperRange() = upperRange

    companion object {
        fun valueOfCategoryBmi(resources: Resources, value: String): BmiCategories {
            for (enum in values()) {
                if (resources.getString(enum.typeID) == value) {
                    return enum
                }
            }
            throw IllegalArgumentException(
                "No enum const " + BmiCategories::class.java + "@nameCategory." + value
            )
        }
    }
}
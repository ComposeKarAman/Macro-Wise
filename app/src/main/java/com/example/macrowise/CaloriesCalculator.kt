package com.example.macrowise

class Calculation(weight: Double, activity: Double, height: Double, age: Int, gender: String){
    val calories = if(gender == "Male") {
        (((10 * weight) + (6.25 * height) - (5 * age) + 5) * activity)
    }else{
        (((10 * weight) + (6.25 * height) - (5 * age) - 161) * activity)
    }
    val protein = (calories * 0.2)/4
    val carbohydrate = (calories * 0.5)/4
    val fat = (calories * 0.3)/9
}
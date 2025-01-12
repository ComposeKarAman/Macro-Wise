package com.example.macrowise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.macrowise.ui.theme.MacroWiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MacroWiseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MacroWiseUI(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MacroWiseUI(modifier: Modifier) {

    var age by remember { mutableStateOf("") }
    var heightCm by remember { mutableStateOf("") }
    var heightFeet by remember { mutableStateOf("") }
    var heightInch by remember { mutableStateOf("") }
    var heightUnit by remember { mutableStateOf("cm") }
    var weight by remember { mutableStateOf("") }
    var weightLbs by remember { mutableStateOf("") }
    var weightUnit by remember { mutableStateOf("kg") }
    var gender by remember { mutableStateOf("SELECT") }
    var activity by remember { mutableDoubleStateOf(0.0) }
    var activityBtn by remember { mutableStateOf("SELECT") }
    var genderDDM by remember { mutableStateOf(false) }
    var unit by remember { mutableStateOf("Metric Units")}
    var unitDDM by remember { mutableStateOf(false)}
    var activityDDM by remember { mutableStateOf(false) }
    var alertEnable by remember { mutableStateOf(false) }



    Column(
        Modifier
            .fillMaxSize()
            .padding(46.dp)
    ) {
        Text("Hello There,", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        Column(
            modifier.fillMaxSize(),
            Arrangement.Center
        ) {

            Text("Select your Unit:")
            Box {
                Button({ unitDDM = true }) {
                    Text(unit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(unitDDM, { unitDDM = false }) {
                    DropdownMenuItem({ Text("Metric Units") }, {
                        unitDDM = false
                        unit = "Metric Units"
                    })
                    DropdownMenuItem({ Text("US Units") }, {
                        unitDDM = false
                        unit = "US Units"
                        weightUnit = "pounds"
                    })
                }
            }

            Text("Enter your Age:")
            OutlinedTextField(
                age,
                { age = it }
            )


            Text("Enter your Height:")
            if (unit == "Metric Units") {
                OutlinedTextField(
                    heightCm,
                    { heightCm = it },
                    label = { Text("cm") }
                )
            }
            else {
                Column {
                    OutlinedTextField(
                        heightFeet,
                        { heightFeet = it },
                        label = { Text("feet") })

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        heightInch,
                        { heightInch = it },
                        label = { Text("inch") })

                    if (heightFeet.isNotEmpty() && heightInch.isNotEmpty()) {
                        heightCm = ((heightFeet.toIntOrNull() ?: 1) * 30.48 +( heightInch.toIntOrNull() ?: 1) * 2.54).toString()
                    }
                }
            }

            Text("Enter your Weight:")
            if (unit == "Metric Units") {
                OutlinedTextField(
                    weight,
                    { weight = it },
                    label = { Text(weightUnit) }
                )
            } else{
                OutlinedTextField(
                    weightLbs,
                    { weightLbs = it },
                    label = { Text(weightUnit) })

                if(weight.isNotEmpty()){
                    weight = ((weightLbs.toDoubleOrNull() ?: 1.0) * 0.453592).toString()
                }
            }

            Text("Select your Gender:")
            Box {
                Button({ genderDDM = true }) {
                    Text(gender)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(genderDDM, { genderDDM = false }) {
                    DropdownMenuItem({ Text("Male") },
                        {
                            gender = "Male"
                            genderDDM = false
                        })
                    DropdownMenuItem({ Text("Female") },
                        {
                            gender = "Female"
                            genderDDM = false
                        })
                }
            }

            Text("Select your Activity Level:")
            Box {
                Button({ activityDDM = true }) {
                    Text(activityBtn)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(activityDDM, { activityDDM = false }) {
                    DropdownMenuItem({ Text("Low Activity") },
                        {
                            activityDDM = false
                            activityBtn = "Low Activity"
                            activity = 1.375
                        })

                    DropdownMenuItem({ Text("Moderate Activity") },
                        {
                            activityDDM = false
                            activityBtn = "Moderate Activity"
                            activity = 1.55
                        })

                    DropdownMenuItem({ Text("High Activity") },
                        {
                            activityDDM = false
                            activityBtn = "High Activity"
                            activity = 1.725
                        })
                }
            }

            Spacer(Modifier.padding(16.dp))

            if (alertEnable) {

                AlertDialog(
                    { alertEnable = false },
                    {
                        Button(
                            {
                                age = ""
                                heightCm = ""
                                heightFeet = ""
                                heightInch = ""
                                heightUnit = "cm"
                                weightUnit = "kg"
                                weight = ""
                                gender = "SELECT"
                                activityBtn = "SELECT"
                                activity = 0.0
                                alertEnable = false
                            }) { Text("Check Another") }
                    },
                    text = {
                        Column {
                            var obj = Calculation(
                                weight.toDouble(),
                                activity,
                                heightCm.toDouble(),
                                age.toInt(),
                                gender
                            )
                            Text(
                                "Your Calories Intake is: ${obj.calories.toInt()}Kal",
                                fontWeight = FontWeight.Bold, fontSize = 18.sp
                            )
                            Text("Protein: ${obj.protein.toInt()}g", fontStyle = FontStyle.Italic)
                            Text("Carbohydrate: ${obj.carbohydrate.toInt()}g", fontStyle = FontStyle.Italic)
                            Text("Fat: ${obj.fat.toInt()}g", fontStyle = FontStyle.Italic)
                        }
                    })
            }

            Button({ alertEnable = true })
            { Text("Calculate", fontWeight = FontWeight.Bold) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MacroWiseTheme {
        MacroWiseUI(Modifier)
    }
}
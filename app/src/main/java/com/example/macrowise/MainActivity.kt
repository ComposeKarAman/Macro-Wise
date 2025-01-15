package com.example.macrowise

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
    var finalHeight by remember { mutableDoubleStateOf(0.0) }
    var weight by remember { mutableStateOf("") }
    var weightLbs by remember { mutableStateOf("") }
    var finalWeight by remember { mutableDoubleStateOf(0.0) }
    var weightUnit by remember { mutableStateOf("kg") }
    var gender by remember { mutableStateOf("SELECT") }
    var activityLevel by remember { mutableDoubleStateOf(0.0) }
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
        Text("Macro Wise", fontWeight = FontWeight.Bold, fontSize = 24.sp)

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
                        weightUnit = "kg"
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
                { age = it },
                label = { Text("Enter your Age") }
            )
            if (age.isNotEmpty()){
                age = (age.toIntOrNull() ?: 0).toString()
            }

            Text("Enter your Height:")
            if (unit == "Metric Units") {
                OutlinedTextField(
                    heightCm,
                    { heightCm = it },
                    label = { Text("cm") }
                )
                if (heightCm.isNotEmpty()) {
                    finalHeight = heightCm.toDoubleOrNull() ?: 0.0
                }
            }
            else if(unit == "US Units"){
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
                    DropdownMenuItem({ Text("little/no exercise") },
                        {
                            activityDDM = false
                            activityBtn = "Low Activity"
                            activityLevel = 1.2
                        })
                    DropdownMenuItem({ Text("light exercise/sports 1-3 days/week") },
                        {
                            activityDDM = false
                            activityBtn = "Low Activity"
                            activityLevel = 1.375
                        })
                    DropdownMenuItem({ Text("moderate exercise 3-5 days/week") },
                        {
                            activityDDM = false
                            activityBtn = "Moderate Activity"
                            activityLevel = 1.55
                        })
                    DropdownMenuItem({ Text("hard exercise 6-7 days/week") },
                        {
                            activityDDM = false
                            activityBtn = "High Activity"
                            activityLevel = 1.725
                        })
                    DropdownMenuItem({ Text("very hard exercise/physical job") },
                        {
                            activityDDM = false
                            activityBtn = "High Activity"
                            activityLevel = 1.9
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
                                weight = ""
                                weightLbs = ""
                                gender = "SELECT"
                                activityBtn = "SELECT"
                                activityLevel = 0.0
                                alertEnable = false
                            }) { Text("Check Another") }
                    },
                    text = {
                        Column {
                            var obj = Calculation(
                                finalWeight,
                                activityLevel,
                                finalHeight,
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

            val context = LocalContext.current

            Button({
                if (unit == "US Units") {
                    if (heightFeet.isNotEmpty() && heightInch.isNotEmpty()) {
                        finalHeight = (heightFeet.toDoubleOrNull() ?: 0.0) * 30.4 +
                                (heightInch.toDoubleOrNull() ?: 0.0) * 2.54
                    }
                    if (weightLbs.isNotEmpty()) {
                        finalWeight = (weightLbs.toDoubleOrNull() ?: 0.0) * 0.453592
                    }
                }

                if(gender == "SELECT") {
                    Toast.makeText(context, "Please select Gender", Toast.LENGTH_SHORT).show()
                    alertEnable = false
                }
                if(activityBtn == "SELECT"){
                    Toast.makeText(context, "Please select Activity level", Toast.LENGTH_SHORT).show()
                    alertEnable = false
                }
                else{
                    if (age.isEmpty()) age = "0"
                    if (heightCm.isEmpty()) heightCm = ""
                    if (heightFeet.isEmpty()) heightFeet = "0"
                    if (heightInch.isEmpty()) heightInch = "0"
                    if (weight.isEmpty()) weight = ""
                    if (weightLbs.isEmpty()) weightLbs = "0"
                }
                if (gender != "SELECT" && activityBtn != "SELECT") alertEnable = true
            })
            {
                Text("Calculate", fontWeight = FontWeight.Bold) }
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
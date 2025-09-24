package com.example.wedstoryes.customcomposables

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedstoryes.R
import com.example.wedstoryes.ui.theme.LocalTextFieldColors
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    eventDetailsScreen: Boolean,
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Triple<String, String, String>) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val localContext = LocalContext.current
    val timeOptions = listOf("Morning", "Afternoon","Evening")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        mutableStateOf(
            "Select Time"
        )
    }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    if (openDialog) {
        BasicAlertDialog(
            onDismissRequest = { onDismiss() }
        ) {
            androidx.compose.material3.Surface(
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                tonalElevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Event Name",fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold, color =colorResource(R.color.wedstoreys) ,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal)))
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        label = { Text("Enter Event name",fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))) }, shape = RoundedCornerShape(15.dp), colors = LocalTextFieldColors.current)
                    Spacer(Modifier.height(16.dp))
                    if (eventDetailsScreen){
                        val today = LocalDate.now()
                        DateSelector(selectedDate = today, onDateSelected = {selectedDate = it}, label = "Select Date")
                        BoxWithConstraints {
                            val buttonWidth = maxWidth - 16.dp
                            Box {
                                OutlinedButton(
                                    onClick = { expanded = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = selectedOption,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.Start,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = colorResource(R.color.wedstoreys),
                                                fontStyle = FontStyle.Italic)
                                        )
                                        Image(
                                            painter = if (expanded) painterResource(R.drawable.uparrow) else painterResource(R.drawable.arrowdown),
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp).align(Alignment.CenterVertically) // Optional: set a specific size
                                        )
                                    }
                                }

                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                shape = ShapeDefaults.ExtraSmall,
                                modifier = Modifier.width(buttonWidth)
                            ) {

                                timeOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedOption = option
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))

                    }

                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { onDismiss()
                            }, colors = ButtonColors(containerColor = colorResource(R.color.wedstoreys),
                            contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.LightGray), modifier = Modifier.padding(5.dp)) {
                            Text("Cancel")
                        }
                        Button (onClick = {
                            when(eventDetailsScreen){
                                true -> {
                                    if (text.isEmpty()||selectedOption.equals("Select Time")||selectedDate==null){
                                        Toast.makeText(localContext, "Please Fill all the Fields above", Toast.LENGTH_SHORT).show()
                                    }else{
                                        onConfirm(Triple(text,selectedOption,selectedDate.toString()))
                                        onDismiss()
                                    }
                                }
                                false -> {
                                    if (text.isEmpty()){
                                        Toast.makeText(localContext, "Please Fill all the Fields above", Toast.LENGTH_SHORT).show()
                                    }else{
                                        onConfirm(Triple(text,"",""))
                                        onDismiss()
                                    }
                                }

                            }

                        },colors = ButtonColors(containerColor = colorResource(R.color.wedstoreys),
                            contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.LightGray), modifier = Modifier.padding(5.dp)) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

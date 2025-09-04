package com.example.wedstoryes.customcomposables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedstoryes.R
import com.example.wedstoryes.ui.theme.LocalTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val localContext = LocalContext.current

    if (openDialog) {
        BasicAlertDialog(
            onDismissRequest = { onDismiss() }
        ) {
            androidx.compose.material3.Surface(
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                tonalElevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Enter Event Name",fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold, color =colorResource(R.color.wedstoreys) ,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal)))
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Event name",fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))) }, shape = RoundedCornerShape(15.dp), colors = LocalTextFieldColors.current)
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { onDismiss()
                            }, colors = ButtonColors(containerColor = colorResource(R.color.wedstoreys),
                            contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.LightGray), modifier = Modifier.padding(5.dp)) {
                            Text("Cancel")
                        }
                        Button (onClick = {
                            if (text.isEmpty()){
                                Toast.makeText(localContext, "Please enter event name", Toast.LENGTH_SHORT).show()
                            }else{
                                onConfirm(text)
                                onDismiss()
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

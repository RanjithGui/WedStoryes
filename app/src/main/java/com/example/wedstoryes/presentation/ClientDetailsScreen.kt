package com.example.wedstoryes.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.wedstoryes.R
import com.example.wedstoryes.presentation.events.GlobalEvent


@Composable
fun ClientDetailsScreen(viewmodel: GlobalViewmodel, onEvent: (GlobalEvent) -> Unit, navController: NavController){
    val state: GlobalState by viewmodel.state.collectAsStateWithLifecycle()


    Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp).fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 60.dp)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .clickable { navController.popBackStack() },
            )

                Text(
                    text = "Client Details",
                    modifier = Modifier
                        .padding(top = 54.dp, start = 48.dp, end = 24.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.wedstoreys),
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))
                )

        }
        Spacer(modifier = Modifier.size(15.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            item {
                OwnerDetails()
            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
            item {
                ClientDetails()
            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
            item {
                TermsAndConditions()
            }
        }
        androidx.compose.material3.Button(
            onClick = {

            }, enabled = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            colors = ButtonColors(containerColor = colorResource(R.color.wedstoreys),
                contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.LightGray)
        ) {
            Text(text = "Continue")
        }
    }

}
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidMobile(mobile: String): Boolean {
    return mobile.length == 10 && mobile.all { it.isDigit() }
}
@Composable
fun OwnerDetails(){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isMobileValid by remember { mutableStateOf(true) }

    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp), elevation = CardDefaults.cardElevation(15.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "Owner Details",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.wedstoreys),
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))
            )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            value = name,
            onValueChange = { newValue ->
               name = newValue
            },
            label = { Text("Name") },
            placeholder = { Text("Enter Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "name",
                    modifier = Modifier.size(18.dp)
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.wedstoreys),
                fontStyle = FontStyle.Italic
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            value = mobileNumber,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }.take(10)
                mobileNumber = filteredValue
                isMobileValid = filteredValue.isEmpty() || isValidMobile(filteredValue)
            },
            label = { Text("Mobile Number") },
            placeholder = { Text("Enter Mobile Number") },
            singleLine = true,
            isError = !isMobileValid,
            supportingText = {
                if (!isMobileValid) {
                    Text(
                        text = "Please enter a valid 10-digit mobile number",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Mobile number",
                    modifier = Modifier.size(18.dp),
                    tint = if (!isMobileValid) MaterialTheme.colorScheme.error else LocalContentColor.current
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.wedstoreys),
                fontStyle = FontStyle.Italic
            )
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            value = email,
            onValueChange = { newValue ->
                email = newValue
                isEmailValid = newValue.isEmpty() || isValidEmail(newValue)
            },
            label = { Text("Email") },
            placeholder = { Text("Enter Email") },
            singleLine = true,
            isError = !isEmailValid,
            supportingText = {
                if (!isEmailValid) {
                    Text(
                        text = "Please enter a valid email address",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    modifier = Modifier.size(18.dp),
                    tint = if (!isEmailValid) MaterialTheme.colorScheme.error else LocalContentColor.current
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.wedstoreys),
                fontStyle = FontStyle.Italic
            )
        )

    }
    }
}
@Composable
fun ClientDetails(){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isMobileValid by remember { mutableStateOf(true) }

    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp), elevation = CardDefaults.cardElevation(15.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "Client Details",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.wedstoreys),
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                value = name,
                onValueChange = { newValue ->
                    name = newValue
                },
                label = { Text("Name") },
                placeholder = { Text("Enter Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "name",
                        modifier = Modifier.size(18.dp)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.wedstoreys),
                    fontStyle = FontStyle.Italic
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                value = mobileNumber,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }.take(10)
                    mobileNumber = filteredValue
                    isMobileValid = filteredValue.isEmpty() || isValidMobile(filteredValue)
                },
                label = { Text("Mobile Number") },
                placeholder = { Text("Enter Mobile Number") },
                singleLine = true,
                isError = !isMobileValid,
                supportingText = {
                    if (!isMobileValid) {
                        Text(
                            text = "Please enter a valid 10-digit mobile number",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Mobile number",
                        modifier = Modifier.size(18.dp),
                        tint = if (!isMobileValid) MaterialTheme.colorScheme.error else LocalContentColor.current
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.wedstoreys),
                    fontStyle = FontStyle.Italic
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    isEmailValid = newValue.isEmpty() || isValidEmail(newValue)
                },
                label = { Text("Email") },
                placeholder = { Text("Enter Email") },
                singleLine = true,
                isError = !isEmailValid,
                supportingText = {
                    if (!isEmailValid) {
                        Text(
                            text = "Please enter a valid email address",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        modifier = Modifier.size(18.dp),
                        tint = if (!isEmailValid) MaterialTheme.colorScheme.error else LocalContentColor.current
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.wedstoreys),
                    fontStyle = FontStyle.Italic
                )
            )

        }
    }

}
@Composable
fun TermsAndConditions(){
    var termsText by remember { mutableStateOf("1. ") }
    var cursorPosition by remember { mutableStateOf(3) }

    fun handleTextChange(newValue: String) {
        val oldLines = termsText.split("\n")
        val newLines = newValue.split("\n")

        // Check if user pressed Enter (new line added)
        if (newLines.size > oldLines.size) {
            val lastLineIndex = newLines.size - 1
            val currentLine = newLines[lastLineIndex]

            // If the new line doesn't start with a number, add numbering
            if (!currentLine.matches(Regex("^\\d+\\. .*"))) {
                val nextNumber = newLines.size
                val numberedLine = "$nextNumber. $currentLine"
                val updatedLines = newLines.toMutableList()
                updatedLines[lastLineIndex] = numberedLine
                termsText = updatedLines.joinToString("\n")
            } else {
                termsText = newValue
            }
        }
        // Check if user deleted a line
        else if (newLines.size < oldLines.size) {
            // Renumber all lines after deletion
            val renumberedLines = newLines.mapIndexed { index, line ->
                val content = line.replaceFirst(Regex("^\\d+\\. "), "")
                "${index + 1}. $content"
            }
            termsText = renumberedLines.joinToString("\n")
        }
        // Normal text editing within a line
        else {
            termsText = newValue
        }
    }

    val lines = termsText.split("\n")

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = termsText,
            onValueChange = { newValue ->
                handleTextChange(newValue)
            },
            label = { Text("Terms and Conditions") },
            placeholder = {
                Text("1. Enter your first term and press Enter\n2. New lines will be auto-numbered\n3. TextField expands automatically")
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp), // Minimum height
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = colorResource(R.color.wedstoreys)
            ),
            supportingText = {
                Text("Lines: ${lines.size} | Characters: ${termsText.length}")
            }
        )
        Text(
            text = "Tip: Press Enter to create a new numbered line",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }

}
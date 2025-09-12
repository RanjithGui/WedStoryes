package com.example.wedstoryes.presentation

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.wedstoryes.R
import com.example.wedstoryes.customcomposables.AnimatedFabWithOptions
import com.example.wedstoryes.customcomposables.GifImage
import com.example.wedstoryes.data.Addons
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.data.Photographers
import com.example.wedstoryes.data.Videographers
import com.example.wedstoryes.presentation.events.GlobalEvent
import kotlin.collections.emptyList

@Composable
fun EventDetailsScreen(viewmodel: GlobalViewmodel, onEvent: (GlobalEvent) -> Unit, navController: NavController) {
    val state: GlobalState by viewmodel.state.collectAsStateWithLifecycle()
    val localContext = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.back_button),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 60.dp, start = 24.dp)
                        .size(24.dp)
                        .align(Alignment.TopStart)
                        .clickable { navController.popBackStack() },
                )

                Text(
                    text = if (state.selectedEventItem != null) state.selectedEventItem!!.title else "",
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

            if (state.eventDetails != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp)
                        .padding(bottom = 90.dp),
                    contentPadding = PaddingValues(top = 5.dp, bottom = 5.dp)
                ) {
                    state.eventDetails?.photographers?.let { photographers ->
                        if (photographers.isNotEmpty()) {
                            items(photographers) { photographer ->
                                EventDetailsItem(
                                    label = "Photo",
                                    onEvent,
                                    photographer = photographer,
                                    videographer = Videographers(),
                                    addons = Addons()
                                )
                            }
                        }
                    }
                    state.eventDetails?.videographers?.let { videographers ->
                        if (videographers.isNotEmpty()) {
                            items(videographers) { videographer ->
                                EventDetailsItem(
                                    label = "Video",
                                    onEvent,
                                    photographer = Photographers(),
                                    videographer = videographer,
                                    addons = Addons()
                                )
                            }
                        }
                    }
                    state.eventDetails?.addons?.let { addons ->
                        if (addons.isNotEmpty()) {
                            items(addons) { addon ->
                                EventDetailsItem(
                                    label = "Addons",
                                    onEvent,
                                    photographer = Photographers(),
                                    videographer = Videographers(),
                                    addons = addon
                                )
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add photo and video options here",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
        }
        AnimatedFabWithOptions(
            onFabOptionClick = { label ->
                when (label) {
                    "Photo" -> {
                        onEvent(
                            GlobalEvent.onAddEventDetails(
                                "Photo",
                                state.selectedEventItem?.title ?: "",
                                Photographers(0, "Select Type", "", ""),
                                Videographers(),
                                Addons()
                            )
                        )
                    }
                    "Video" -> {
                        onEvent(
                            GlobalEvent.onAddEventDetails(
                                "Video",
                                state.selectedEventItem?.title ?: "",
                                Photographers(),
                                Videographers(0, "Select Type", "", ""),
                                Addons()
                            )
                        )
                    }
                    "Addons" -> {
                        onEvent(
                            GlobalEvent.onAddEventDetails(
                                "Addons",
                                state.selectedEventItem?.title ?: "",
                                Photographers(),
                                Videographers(),
                                Addons(
                                    "Select Type",
                                    albumsNos = 0,
                                    albumsPrice = 0.0,
                                    drone = null,
                                    ledScreen = null,
                                    liveStreaming = null,
                                    makeupArtist = null,
                                    decorations = null,
                                    invitations = null,
                                    ledScreenPrice = null,
                                    ledScreenCount = null,
                                    dronePrice = null,
                                    liveStreamingPrice = null,
                                    makeupArtistPrice = null,
                                    decorationsPrice = null,
                                    invitationsPrice = null
                                )
                            )
                        )
                    }
                }
            }
        )
    }
}
@Composable
fun EventDetailsItem(label: String,onEvent: (GlobalEvent)-> Unit,photographer: Photographers,videographer: Videographers,addons: Addons) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Type") }
    val pvOptions = listOf("Select Type","Traditional", "Candid")
    val otherOptions = listOf("Select Type","Drone", "Albums","Led Screen","Live Streaming","Makeup Artist","Decorations","Invitations")
    var count by remember { mutableStateOf(0) }
    var priceText by remember { mutableStateOf("") }


    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp), shape = RoundedCornerShape(5.dp)) {
        Column (modifier = Modifier.fillMaxSize()){
            Text(text = when(label){
                "Photo" -> "Photographer"
                "Video" -> "Videographer"
                "Addons" -> "Addons"
                else -> ""
            }, modifier = Modifier.padding(start = 16.dp,top=16.dp).fillMaxWidth(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.SemiBold))
            )
            BoxWithConstraints {
                val buttonWidth = maxWidth - 16.dp
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.padding(16.dp, end = 16.dp).fillMaxWidth(),
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
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(8.dp).width(buttonWidth)
                    ) {
                        var options =listOf<String>()
                        when(label){
                            "Photo" -> options = pvOptions
                            "Video" -> options =pvOptions
                            "Addons" -> options =otherOptions
                        }
                        options.forEach { option ->
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
            }

            Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                var rawPrice by remember { mutableStateOf("") }
                var displayPrice by remember { mutableStateOf("") }
                OutlinedTextField(
                    modifier = Modifier.weight(5f).padding(vertical = 4.dp),
                    shape = RoundedCornerShape(35.dp),
                    value = displayPrice,
                    onValueChange = { newValue ->

                        val digitsOnly = newValue.filter { it.isDigit() }

                        if (digitsOnly.length <= 10) {
                            rawPrice = digitsOnly
                            displayPrice = if (digitsOnly.isNotEmpty()) {
                                formatNumberWithCommas(digitsOnly)
                            } else {
                                ""
                            }
                        }
                    },
                    label = { Text("Price") },
                    placeholder = { Text("Enter Price") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.rupee),
                            contentDescription = "Rupee icon",
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
                Spacer(modifier = Modifier.weight(1f))
                CounterButtons(count, onValueChange = {
                    count = it
                },
                    modifier = Modifier
                        .weight(3f)
                        .align(Alignment.CenterVertically),
                    minValue = 1,
                    maxValue = 20,
                    step = 1
                )

            }

            // Add your other content here
        }
    }
}
fun formatNumberWithCommas(number: String): String {
    if (number.isEmpty()) return ""
    return try {
        val num = number.replace(",", "").toLongOrNull()
        if (num != null) {
            java.text.NumberFormat.getInstance().format(num)
        } else {
            number
        }
    } catch (e: Exception) {
        number
    }
}
@Composable
fun CounterButtons(
    value: Int = 0,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Decrement Button
        OutlinedButton(
            onClick = {
                val newValue = value - step
                if (newValue >= minValue) {
                    onValueChange(newValue)
                }
            },
            enabled = value > minValue,
            modifier = Modifier.size(22.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease",
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = value.toString(),
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .widthIn(min = 40.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        // Increment Button
        OutlinedButton(
            onClick = {
                val newValue = value + step
                if (newValue <= maxValue) {
                    onValueChange(newValue)
                }
            },
            enabled = value < maxValue,
            modifier = Modifier.size(22.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
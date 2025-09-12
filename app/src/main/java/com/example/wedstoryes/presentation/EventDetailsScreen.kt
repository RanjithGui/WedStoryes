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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.wedstoryes.R
import com.example.wedstoryes.customcomposables.AnimatedFabWithOptions
import com.example.wedstoryes.customcomposables.GifImage
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.presentation.events.GlobalEvent
import kotlin.collections.emptyList

@Composable
fun EventDetailsScreen(viewmodel: GlobalViewmodel,onEvent: (GlobalEvent)-> Unit,navController: NavController) {
    val state : GlobalState by viewmodel.state.collectAsStateWithLifecycle()
    val localContext = LocalContext.current
    Box(modifier = Modifier.fillMaxSize().padding(75.dp), contentAlignment = Alignment.BottomEnd) {
        AnimatedFabWithOptions(onFabOptionClick = { label ->
            println("FAB Option clicked: $label")
            println("FAB Option clicked: $label")
            when (label) {
                "Photo" -> {
                    onEvent(GlobalEvent.onAddEventDetails("Photo",
                        state.selectedEventItem?.title ?: ""
                    ))
                }
                "Video" -> {
                    onEvent(GlobalEvent.onAddEventDetails("Video",state.selectedEventItem?.title ?: ""))
                }
                "Others" -> {
                    onEvent(GlobalEvent.onAddEventDetails("Others",state.selectedEventItem?.title ?: ""))
                }
            }
        })
    }
    Column {
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
                    .padding(top = 54.dp, start = 48.dp, end = 24.dp) // Account for button space
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
        if (state.eventDetails != null){
            LazyColumn (modifier = Modifier.wrapContentSize()){
                item {
                    if (state.eventDetails?.photographers != null){
                        EventDetailsItem(label = "Photo", onEvent=onEvent)
                    }
                }
            }

        }else{
            Column (modifier = Modifier.fillMaxWidth().padding(top=200.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "add photo and video options here", modifier = Modifier.padding(24.dp).fillMaxWidth(), textAlign = TextAlign.Center, style = TextStyle(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic)
            }
        }


    }
}
@Composable
fun EventDetailsItem(label: String,onEvent: (GlobalEvent)-> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Type") }
    val pvOptions = listOf("Select Type","Traditional", "Candid")
    val otherOptions = listOf("Select Type","Drone", "Albums","Led Screen","Live Streaming","Makeup Artist","Decorations","Invitations")
    var count by remember { mutableStateOf(0) }

    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(5.dp)) {
        Column (modifier = Modifier.fillMaxSize()){
            Text(text = when(label){
                "Photo" -> "Photographer"
                "Video" -> "Videographer"
                "Others" -> "Other Services"
                else -> ""
            }, modifier = Modifier.padding(start = 16.dp,top=16.dp).fillMaxWidth(), fontSize = 26.sp, fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.SemiBold))
            )
            BoxWithConstraints {
                val buttonWidth = maxWidth - 16.dp
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.padding(16.dp, bottom = 5.dp, end = 16.dp).fillMaxWidth(),
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
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(R.color.wedstoreys),
                                    fontStyle = FontStyle.Italic)
                                )
                            Image(
                                painter = if (expanded) painterResource(R.drawable.uparrow) else painterResource(R.drawable.arrowdown),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp).align(Alignment.CenterVertically) // Optional: set a specific size
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

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                // Dropdown menu
                OutlinedTextField(modifier = Modifier.weight(5f), shape = RoundedCornerShape(35.dp),
                    value = "",
                    onValueChange = { /* Handle text change */ },
                    label = { Text("Price") },
                    placeholder = { Text("Enter Price") },
                    singleLine = true,
                    leadingIcon ={Icon(
                        painter = painterResource(id = R.drawable.rupee),
                        contentDescription = "Rupee icon", modifier = Modifier.size(18.dp)
                    )},
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.wedstoreys),
                        fontStyle = FontStyle.Italic)
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
            modifier = Modifier.size(28.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease",
                modifier = Modifier.size(20.dp)
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
            modifier = Modifier.size(28.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
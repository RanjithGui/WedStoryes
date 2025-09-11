package com.example.wedstoryes.presentation

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
            LazyColumn (modifier = Modifier.fillMaxSize()){
                item {
                    if (state.eventDetails?.photographers != null){
                        EventDetailsItem(label = "Photo", onEvent=onEvent)
                    }
                }
            }

        }else{
            Column (modifier = Modifier.fillMaxWidth().padding(top=200.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "add photo and video options here", modifier = Modifier.padding(24.dp).fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(75.dp), contentAlignment = Alignment.BottomEnd) {
            AnimatedFabWithOptions(onFabOptionClick = { label ->
                println("FAB Option clicked: $label")
                println("FAB Option clicked: $label")
                when (label) {
                    "Photo" -> {
                        onEvent(GlobalEvent.onAddEventDetails("Photo"))
                        Toast.makeText(localContext,"You can't delete default events", Toast.LENGTH_SHORT).show()
                    }
                    "Video" -> {
                        onEvent(GlobalEvent.onAddEventDetails("Video"))
                    }
                    "Others" -> {
                        onEvent(GlobalEvent.onAddEventDetails("Others"))
                    }
                }
            })
        }

    }
}
@Composable
fun EventDetailsItem(label: String,onEvent: (GlobalEvent)-> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Type") }
    val pvOptions = listOf("Select Type","Traditional", "Candid")
    val otherOptions = listOf("Select Type","Drone", "Album")

    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(5.dp)) {
        Column (modifier = Modifier.fillMaxSize()){
            BoxWithConstraints {
                val buttonWidth = maxWidth - 16.dp
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = selectedOption,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
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
                            "Others" -> options =otherOptions
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

            Row(modifier = Modifier.fillMaxWidth()) {
                // Dropdown menu

                Spacer(modifier = Modifier.weight(1f))
            }

            // Add your other content here
        }
    }
}
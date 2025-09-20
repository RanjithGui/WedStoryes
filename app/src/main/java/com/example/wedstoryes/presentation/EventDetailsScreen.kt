package com.example.wedstoryes.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
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
import com.example.wedstoryes.customcomposables.CustomAlertDialog
import com.example.wedstoryes.customcomposables.ExpandableCard
import com.example.wedstoryes.data.Addons
import com.example.wedstoryes.data.Photographers
import com.example.wedstoryes.data.SubEventDetails
import com.example.wedstoryes.data.Videographers
import com.example.wedstoryes.presentation.events.GlobalEvent

@Composable
fun EventDetailsScreen(viewmodel: GlobalViewmodel, onEvent: (GlobalEvent) -> Unit, navController: NavController) {
    val state: GlobalState by viewmodel.state.collectAsStateWithLifecycle()
    var openAlertDialog by remember { mutableStateOf(false) }
    var selectedSubEvent by remember { mutableStateOf("") }
    val eventDetails = state.events.find { eventItem ->
        eventItem.title == state.selectedEventItem?.title
    }?.eventDetails
    Log.d("SelectedEvents", "Selected events: ${eventDetails.toString()}")
    Log.d("SelectedEvents", "Selected events: ${state.events.toString()}")
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(start=24.dp, end = 24.dp)) {
            var isFabExpanded by remember { mutableStateOf(false) }

            if (isFabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clickable { isFabExpanded = false }
                )
            }
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

                if (state.selectedEventItem != null){
                    Text(
                        text = state.selectedEventItem?.title.toString(),
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
            }

            if (eventDetails != null && eventDetails.isNotEmpty()) {
                eventDetails.forEachIndexed { index, details ->
                    selectedSubEvent = details.subEvent.toString()
                   Column(modifier = Modifier.fillMaxWidth().padding(top=10.dp)) {
                       ExpandableCard({
                           Text(
                               text = selectedSubEvent,
                               modifier = Modifier
                                   .fillMaxWidth().padding(start = 16.dp),
                               fontSize = 25.sp,
                               textAlign = TextAlign.Start,
                               fontWeight = FontWeight.SemiBold,
                               color = colorResource(R.color.wedstoreys),
                               fontStyle = FontStyle.Italic,
                               fontFamily = FontFamily.Cursive
                           ) },
                           { SubEventitem(eventDetails,onEvent,state,details.subEvent) },
                           isExpandedText = false)
                   }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                                Addons(),
                                subEvent = selectedSubEvent
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
                                Addons(),
                                subEvent = selectedSubEvent
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
                                    0,
                                    "",
                                    details = ""
                                ), subEvent =selectedSubEvent
                            )
                        )
                    }
                    "Add Event" ->{
                       println("added subevent")
                        openAlertDialog =true
                    }
                }
            }
        , eventDetails = eventDetails,modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp))
        if (openAlertDialog){
            CustomAlertDialog(openDialog= true, onDismiss = {openAlertDialog=false}, onConfirm = {
                onEvent.invoke(GlobalEvent.onAddSubEvent(state.selectedEventItem?.title ?: "", Photographers(), Videographers(), Addons(),it))
        })
        }
    }
}
@Composable()
fun SubEventitem(eventDetails: List<SubEventDetails>, onEvent: (GlobalEvent) -> Unit, state: GlobalState, subEvent: String?){
    val localContext = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .padding(bottom = 90.dp),
        contentPadding = PaddingValues(top = 5.dp, bottom = 5.dp)
    ) {
        val existingDetail = eventDetails.find { it.subEvent == subEvent }

        if (existingDetail?.photographers?.isNotEmpty() ?: false) {
                itemsIndexed(existingDetail.photographers) { index, photographer ->
                    EventDetailsItem(
                        label = "Photo",
                        onEvent,
                        index = index,
                        photographer = photographer,
                        videographer = Videographers(),
                        addons = Addons(),
                        onSave = { photographers1: Photographers?, videographers: Videographers?, addons: Addons? ->
                            photographers1?.let {
                                onEvent.invoke(GlobalEvent.updateEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = index,
                                    label = "Photo",
                                    photographers = it,
                                    videographers = Videographers(),
                                    addons = Addons(),
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Saved", Toast.LENGTH_SHORT).show()
                        },
                        onDelete={ photographers1: Photographers?, videographers1: Videographers?, addons1: Addons? ->
                            photographers1?.let {
                                onEvent.invoke(GlobalEvent.removeEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = index,
                                    label = "Photo",
                                    photographers = it,
                                    videographers = Videographers(),
                                    addons = Addons(),
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }


            if (existingDetail?.videographers?.isNotEmpty() ?: false) {
                itemsIndexed(existingDetail.videographers) { index, videographer ->
                    EventDetailsItem(
                        label = "Video",
                        onEvent,
                        index = index,
                        photographer = Photographers(),
                        videographer = videographer,
                        addons = Addons(),
                        onSave = { photographers1: Photographers?, videographers1: Videographers?, addons: Addons? ->
                            videographers1?.let {
                                onEvent.invoke(GlobalEvent.updateEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = index,
                                    label = "Video",
                                    photographers = Photographers(),
                                    videographers = it,
                                    addons = Addons(),
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Saved", Toast.LENGTH_SHORT).show()
                        },
                        onDelete ={ photographers1: Photographers?, videographers1: Videographers?, addons1: Addons? ->
                            videographers1?.let {
                                onEvent.invoke(GlobalEvent.removeEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = 0,
                                    label = "Video",
                                    photographers = Photographers(),
                                    videographers = it,
                                    addons = Addons(),
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }


            if (existingDetail?.addons?.isNotEmpty() ?: false) {
                itemsIndexed(existingDetail.addons) { index, addon ->
                    EventDetailsItem(
                        label = "Addons",
                        onEvent,
                        index = index,
                        photographer = Photographers(),
                        videographer = Videographers(),
                        addons = addon,
                        onSave ={ photographers1: Photographers?, videographers1: Videographers?, addons1: Addons? ->
                            addons1?.let {
                                onEvent.invoke(GlobalEvent.updateEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = 0,
                                    label = "Addons",
                                    photographers = Photographers(),
                                    videographers = Videographers(),
                                    addons = it,
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Saved", Toast.LENGTH_SHORT).show()
                        },
                        onDelete={ photographers1: Photographers?, videographers1: Videographers?, addons1: Addons? ->
                            addons1?.let {
                                onEvent.invoke(GlobalEvent.removeEventDetails(
                                    eventName = state.selectedEventItem?.title ?: "",
                                    index = 0,
                                    label = "Addons",
                                    photographers = Photographers(),
                                    videographers = Videographers(),
                                    addons = it,
                                    subEvent =subEvent
                                ))
                            }
                            Toast.makeText(localContext, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

    }
}
@Composable
fun EventDetailsItem(
    label: String,
    onEvent: (GlobalEvent) -> Unit,
    photographer: Photographers,
    videographer: Videographers,
    addons: Addons,
    index:Int,
    onSave: (Photographers?, Videographers?, Addons?) -> Unit,
    onDelete: (Photographers?, Videographers?, Addons?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        mutableStateOf(
            when(label) {
                "Photo" -> photographer.type?.takeIf { it.isNotEmpty() } ?: "Select Type"
                "Video" -> videographer.type?.takeIf { it.isNotEmpty() } ?: "Select Type"
                "Addons" -> addons.type?.takeIf { it.isNotEmpty() } ?: "Select Type"
                else -> "Select Type"
            }
        )
    }
    val pvOptions = listOf("Traditional", "Candid")
    val otherOptions = listOf("Drone", "Albums","Led Screen","Live Streaming","Makeup Artist","Decorations","Invitations")
    var rawPrice by remember(label, photographer, videographer, addons) {
        mutableStateOf(
            when(label){
                "Photo" -> photographer.price?.filter { it.isDigit() } ?: ""
                "Video" -> videographer.price?.filter { it.isDigit() } ?: ""
                "Addons" -> addons.price?.filter { it.isDigit() } ?: ""
                else -> ""
            }
        )
    }

    var displayPrice by remember(label, photographer, videographer, addons) {
        mutableStateOf(
            when(label){
                "Photo" -> photographer.price ?: ""
                "Video" -> videographer.price ?: ""
                "Addons" -> addons.price ?: ""
                else -> ""
            }
        )
    }

    // Initialize count state with existing data
    var count by remember(label, photographer, videographer, addons) {
        mutableStateOf(
            when(label){
                "Photo" -> if (photographer.nop != null && photographer.nop != 0) photographer.nop else 1
                "Video" -> if (videographer.nop != null && videographer.nop != 0) videographer.nop else 1
                "Addons" -> if (addons.count != 0) addons.count else 1
                else -> 1
            }
        )
    }


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
                            priceText =displayPrice
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
                CounterButtons(
                    count, onValueChange = {
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
            Row (modifier = Modifier.fillMaxWidth().padding(end = 16.dp, bottom = 16.dp), horizontalArrangement = Arrangement.End){
                OutlinedButton(
                    onClick = {
                    // onDelete()
                        val selectedPhotographer = if (label == "Photo") {
                            photographer.copy(
                                type = selectedOption,
                                nop = count,
                                price = priceText
                            )
                        }else{
                            null
                        }
                        val selectedvideographer = if (label == "Video") {
                            videographer.copy(
                                type = selectedOption,
                                nop = count,
                                price = priceText
                            )
                        }else{
                            null
                        }
                        val selectedAddons = if (label == "Addons") {
                            addons.copy(
                                type = selectedOption,
                                count=count,
                               price = priceText
                            )
                        }else{
                            null
                        }
                        onDelete(selectedPhotographer,selectedvideographer,selectedAddons)
                    },
                    enabled =true,
                    modifier = Modifier.size(22.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(
                    onClick = {
                        val updatedPhotographer = if (label == "Photo") {
                            photographer.copy(
                                type = selectedOption,
                                nop = count,
                                price = priceText
                            )
                        } else null

                        val updatedVideographer = if (label == "Video") {
                            videographer.copy(
                                type = selectedOption,
                                nop = count,
                                price = priceText
                            )
                        } else null

                        val updatedAddon = if (label == "Addons") {
                            addons.copy(
                                type = selectedOption,
                                count=count,
                               price = priceText, details = ""
                            )
                        } else null

                        onSave(updatedPhotographer, updatedVideographer, updatedAddon)
                    },
                    enabled =true,
                    modifier = Modifier.size(22.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Add your other content here
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
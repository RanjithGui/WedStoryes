package com.example.wedstoryes.presentation

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedCard
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
import com.example.wedstoryes.customcomposables.CustomAlertDialog
import com.example.wedstoryes.customcomposables.GifImage
import com.example.wedstoryes.data.EventDetails
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.presentation.events.GlobalEvent

@Composable
fun EventType(viewmodel: GlobalViewmodel,onEvent: (GlobalEvent)-> Unit,navController: NavController,onProceed:()-> Unit) {
    val gridState = rememberLazyGridState()
    var openAlertDialog by remember { mutableStateOf(false) }
    val state : GlobalState by viewmodel.state.collectAsStateWithLifecycle()
    var selectedIndex = state.selectedEventItemIndex
    var deleteIndex by remember { mutableStateOf(-1) }
    val localContext = LocalContext.current

    Column {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Image(painter = painterResource(R.drawable.back_button),contentDescription = null, modifier = Modifier.padding(top =54.dp, start = 24.dp).size(24.dp).clickable{navController.popBackStack()},)
        }
      Column(
          modifier = Modifier
              .fillMaxSize()
      ) {
          val data = state.events
          Box(modifier = Modifier.weight(1f)) {
              LazyVerticalGrid(
                  columns = GridCells.Fixed(2),
                  state = gridState,
                  modifier = Modifier
                      .fillMaxSize(),
                  contentPadding = PaddingValues(6.dp),
                  horizontalArrangement = Arrangement.spacedBy(5.dp),
                  verticalArrangement = Arrangement.spacedBy(5.dp)
              ) {
                  itemsIndexed(data) { index, item ->
                      Lazyitem(
                          categoryName = item.title,
                          videoRes = item.videoUri,
                          isSelected = selectedIndex == index,
                          showDelete = (index == deleteIndex),
                          onClick = {
                              selectedIndex = index
                              if (item.id.equals("customevent")){
                                  openAlertDialog = true
                              }
                              onEvent.invoke(GlobalEvent.onProceedEvent(index))
                          },
                            onLongClick = {
                                if (item.id.equals(item.title)) {
                                    deleteIndex =  index
                                }else{
                                    Toast.makeText(localContext,"You can't delete default events", Toast.LENGTH_SHORT).show()
                                    deleteIndex = -1
                                }
                            },
                          onDelete = {
                              if (item.id == item.title) {
                                  onEvent.invoke(GlobalEvent.onDeleteEvent(EventItem(
                                      id = item.id,
                                      title = item.title,
                                      videoUri = item.videoUri,
                                      eventDetails = item.eventDetails
                                  )))
                                  deleteIndex = -1
                              }
                          }
                      )
                  }
              }
          }
          Button(
              onClick = {
                  if (selectedIndex!= -1) {
                  onProceed.invoke()
                  viewmodel.updateSelectedEventItem(state.events[selectedIndex]) }
                  else { Toast.makeText(localContext,"Please select the Event", Toast.LENGTH_SHORT).show()} },
              modifier = Modifier
                  .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                  .fillMaxWidth(),
              colors = ButtonColors(
                  containerColor = colorResource(R.color.wedstoreys),
                  contentColor = Color.White,
                  disabledContainerColor = Color.Gray,
                  disabledContentColor = Color.LightGray
              ), enabled = if (selectedIndex >= 0) true else false,
          ) {
              Text(text = if (selectedIndex >= 0) "Proceed" else "Select Event Type", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
          }
      }
        }
    if (openAlertDialog) {
       CustomAlertDialog(openDialog= true, onDismiss = {openAlertDialog=false}, onConfirm = {onEvent.invoke(
           GlobalEvent.onCustomEvent(EventItem(
               id = it,
               title = it,
               videoUri = R.drawable.customevent,
               eventDetails = EventDetails()
           )))
       selectedIndex =state.events.indexOfFirst { true }
       onProceed.invoke()})
    }
}
@Composable
fun Lazyitem(categoryName: String,
             @DrawableRes videoRes: Int,
             onClick: () ->Unit = {},
             isSelected: Boolean=false,
             onLongClick: () -> Unit = {},
             onDelete: () -> Unit = {},
             showDelete: Boolean = false  ) {

    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            ElevatedCard(
                modifier = Modifier.fillMaxSize().padding(15.dp).combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ).then(
                    if (isSelected) Modifier.border(
                        2.dp,
                        colorResource(R.color.wedstoreys),
                        RoundedCornerShape(5.dp)
                    )
                    else Modifier
                ), shape = RoundedCornerShape(5.dp)
            ) {
                GifImage(videoRes, modifier = Modifier.fillMaxSize())
            }
            if (showDelete) {
                ElevatedCard(
                    onClick = onDelete,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(28.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    GifImage(
                        imageRes = R.drawable.delete, modifier = Modifier.fillMaxSize().clickable{ onDelete() }
                    )
                }
            }

        }
        Text(
            text = categoryName,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold, color = colorResource(R.color.wedstoreys),
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal)),
        )
    }

}
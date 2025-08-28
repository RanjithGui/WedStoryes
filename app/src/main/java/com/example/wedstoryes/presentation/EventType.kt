package com.example.wedstoryes.presentation

import android.widget.Toast
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.wedstoryes.R
import com.example.wedstoryes.customcomposables.CustomVideoPlayer
import com.example.wedstoryes.data.EventItem

@Composable
fun EventType() {
    val gridState = rememberLazyGridState()
    val localContext = LocalContext.current
    var selectedIndex by remember { mutableStateOf(-1) }
    Column {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Image(painter = painterResource(R.drawable.back_button),contentDescription = null, modifier = Modifier.padding(top =54.dp, start = 24.dp))
        }
      Column(
          modifier = Modifier
              .fillMaxSize()
      ) {
          val data = remember {
              listOf(
                  EventItem("wedding", "Wedding", R.raw.wedding),
                  EventItem("baby_shower", "Baby Shower", R.raw.babyshower),
                  EventItem("corporate", "Corporate", R.raw.corporate),
                  EventItem("birthday", "Birthday Party", R.raw.birthday),
                  EventItem("customevent", "Custom Event", R.raw.customevent),
              )
          }

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
                          onClick = {
                              selectedIndex = index
                              if (item.id.equals("customevent")){
                                  Toast.makeText(localContext, "Custom Event Selected", Toast.LENGTH_SHORT).show()
                              }
                          }
                      )
                  }
              }
          }
          Button(
              onClick = { },
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


    }
@Composable
fun Lazyitem(categoryName: String, @RawRes videoRes: Int,onClick: () ->Unit = {},isSelected: Boolean=false) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        ElevatedCard(modifier = Modifier.fillMaxSize().padding(5.dp).clickable{onClick()}.then(if (isSelected)  Modifier.border(2.dp, colorResource(R.color.wedstoreys), RoundedCornerShape(5.dp))
        else Modifier), shape = RoundedCornerShape(5.dp)) {
            CustomVideoPlayer(videoRes)
        }
        Text(
            text = categoryName,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W300, color =colorResource(R.color.wedstoreys) ,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal)),
        )

    }

}
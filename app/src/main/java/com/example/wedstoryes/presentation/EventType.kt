package com.example.wedstoryes.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wedstoryes.R

@Composable
fun EventType() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Image(painter = painterResource(R.drawable.back_button),contentDescription = null, modifier = Modifier.padding(top =54.dp, start = 24.dp))




    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Lazyitem()
    }

}
@Composable
fun Lazyitem(){
    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(5.dp)) {
        Row (modifier = Modifier.fillMaxWidth()){
           Image(painter = painterResource(R.drawable.camera), contentDescription = null, modifier = Modifier.padding(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Wedding")
                Text(text = "Description")
                Text(text = "Starting from")
                Text(text = "$99")
            }
        }
    }
}
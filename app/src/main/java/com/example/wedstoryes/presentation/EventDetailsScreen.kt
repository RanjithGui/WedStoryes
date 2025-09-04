package com.example.wedstoryes.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.wedstoryes.R
import com.example.wedstoryes.presentation.events.GlobalEvent

@Composable
fun EventDetailsScreen(viewmodel: GlobalViewmodel,onEvent: (GlobalEvent)-> Unit,navController: NavController) {
    val state : GlobalState by viewmodel.state.collectAsStateWithLifecycle()
    Column {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Row { Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = null,
                modifier = Modifier.padding(top = 54.dp, start = 24.dp).size(24.dp)
                    .clickable { navController.popBackStack() },
            )
                Text(text = if (state.selectedEventItem!= null) state.selectedEventItem!!.title else "",
                    modifier = Modifier
                        .padding(top = 54.dp, start = 16.dp)
                        .align(Alignment.CenterVertically),
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }

}
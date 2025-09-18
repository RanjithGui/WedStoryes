package com.example.wedstoryes.customcomposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.wedstoryes.data.SubEventDetails

@Composable
fun AnimatedFabWithOptions(onFabOptionClick: (String) -> Unit = {}, eventDetails: List<SubEventDetails>?) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable { isExpanded = false }
            )
        }
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End
        ) {
            // Option buttons (appear above main FAB)
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300, easing = EaseOutBack)
                ) + fadeIn(animationSpec = tween(300)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(200))
            ) {
                if (eventDetails != null && eventDetails.isNotEmpty()){
                    FabOption(
                        icon = painterResource(R.drawable.camera),
                        label = "Add Event",
                        onClick = {
                            onFabOptionClick("Add Event")
                            isExpanded = false
                        }
                    )
                }else {
                    Column(
                        modifier = Modifier.padding(end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        FabOption(
                            icon = painterResource(R.drawable.camera),
                            label = "Photo",
                            onClick = {
                                onFabOptionClick("Photo")
                                isExpanded = false
                            }
                        )
                        FabOption(
                            icon = painterResource(R.drawable.outline_videocam_24),
                            label = "Video",
                            onClick = {
                                onFabOptionClick("Video")
                                isExpanded = false
                            }
                        )
                        FabOption(
                            icon = painterResource(R.drawable.heart_icon),
                            label = "Addons",
                            onClick = {
                                onFabOptionClick("Addons")
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            FloatingActionButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.size(95.dp).padding(bottom = 45.dp, end = 45.dp),
                containerColor = Color.White,
                shape = RoundedCornerShape(35.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = if (isExpanded) "Close" else "Add",
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        rotationZ = if (isExpanded) 45f else 0f
                    }
                )

            }
        }
    }
}

@Composable
fun FabOption(
    icon: Painter,
    label: String,
    onClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Label
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp,
            modifier = Modifier.padding(end = 8.dp).clickable{onClick()}
        ) {
            Row (horizontalArrangement = Arrangement.Center){
                Text(
                text = label,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold, color = colorResource(R.color.wedstoreys),
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily(Font(R.font.italianno_regular, FontWeight.Normal))
            )
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).size(24.dp)
                )
            }

        }
    }
}


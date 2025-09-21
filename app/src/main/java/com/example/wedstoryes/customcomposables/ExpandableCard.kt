package com.example.wedstoryes.customcomposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wedstoryes.R


@Composable
fun ExpandableCard(
    headerContent: @Composable () -> Unit,
    itemContent: @Composable () -> Unit,
    isExpandedText: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(isExpandedText) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
                onExpandedChange(isExpanded)
            }
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    headerContent()
                }
                Icon(
                    painter = painterResource(
                        id = if (isExpanded) {
                            R.drawable.uparrow
                        } else {
                            R.drawable.arrowdown
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 24.dp)
                        .size(20.dp)
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                itemContent()
            }
        }
    }

    if (!isExpanded)
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
        )
}
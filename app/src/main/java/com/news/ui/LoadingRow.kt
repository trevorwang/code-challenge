package com.news.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoadingRow(text: String = "Loading...") {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(48.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp, 24.dp))
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}

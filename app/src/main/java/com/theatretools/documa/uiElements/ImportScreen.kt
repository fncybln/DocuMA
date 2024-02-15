package com.theatretools.documa.uiElements

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

@Composable
fun ImportScreen(modifier: Modifier, importClick: () -> Unit) {
    Row {

        IconButton (onClick = importClick) {
            Icon(Icons.Filled.Build, contentDescription = null)
            Text(AnnotatedString("Import from MA Export"))

        }
    }
}
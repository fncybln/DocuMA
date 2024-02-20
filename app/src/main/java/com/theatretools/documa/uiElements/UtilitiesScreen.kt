package com.theatretools.documa.uiElements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun UtilitiesScreen(modifier: Modifier, clearoutDatabase: () -> Unit) {
    Button(onClick = { clearoutDatabase() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red,)) {
        Icon(Icons.Default.Clear, null)
        Text("Clearout Database")
    }
}
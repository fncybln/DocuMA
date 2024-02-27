package com.theatretools.documa.uiElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun UtilitiesScreen(
    modifier: Modifier,
    ip: String?,
    showfileName: String,
    clearoutDatabase: () -> Unit,
    updateIP: (ip: String) -> Unit,
    updateShowfile: (name: String) -> Unit) {
    var showfile by remember { mutableStateOf(showfileName) }
    var telnetIP by remember { mutableStateOf(ip?:"not set") }

    Column {
        TextField(
            value = showfile,
            onValueChange = { showfile = it },
            label = {Text(text = AnnotatedString("Telnet IP")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
            keyboardOptions = KeyboardOptions(),
        )
        Button(onClick ={updateShowfile(showfile)}) {
            Text("Save")
        }
        Spacer(Modifier.height(8.dp))
        TextField(
            value = telnetIP,
            onValueChange = { telnetIP = it },
            label = {Text(text = AnnotatedString("Telnet IP")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
            keyboardOptions = KeyboardOptions(),
        )
        Button(onClick ={updateIP(telnetIP)}) {
            Text("Save")
        }
        Spacer(Modifier.height(8.dp))
        Text("== DANGER ZONE ==")
        Button(
            onClick = { clearoutDatabase() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Icon(Icons.Default.Clear, null)
            Text("Clearout Database")
        }
    }
}
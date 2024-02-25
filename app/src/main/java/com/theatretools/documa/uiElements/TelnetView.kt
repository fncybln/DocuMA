package com.theatretools.documa.uiElements

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun TelnetView(outputText: String?, sendAction : (cmd: String?)-> Unit, connectAction:()-> Unit) {

    val scrollState = rememberScrollState()
    var output by remember { mutableStateOf(outputText) }

    Column (modifier = Modifier.padding(20.dp),) {
        Button ( modifier = Modifier.padding(top = 20.dp), onClick = {connectAction()}){
            Icon(Icons.Filled.KeyboardArrowRight, null)
            Text(text = "Connect to GrandMa2")
        }

        var cmdInput by remember { mutableStateOf("LOGIN \"luce\" \"27011966\"")}
        TextField(
            value = cmdInput,
            onValueChange = { cmdInput = it },
            label = { -> Text(text = AnnotatedString("CMD INPUT")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 20.dp),
            keyboardOptions = KeyboardOptions(),
        )
        Button ( modifier = Modifier.padding(top = 20.dp), onClick = {sendAction(cmdInput)}){
            Icon(Icons.Filled.Check, null)
            Text(text = "Send")
        }

        Text(output?: "no output", modifier = Modifier.verticalScroll(scrollState))

        if (output == "IndexOutOfBoundsException") {
            Text("Ip Address is not set yet! Please set IP adress in the 'Utilities' Tab and try again.")
        }


    }
}
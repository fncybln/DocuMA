package com.theatretools.documa.uiElements

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.telnet.TelnetClient

@Composable
fun TelnetView(appViewModel: AppViewModel,
               lifecyleOwner: LifecycleOwner,
               outputText: String?,
               sendAction : (cmd: String?)-> Unit,
               connectAction:()-> Unit,
) {

    val scrollState = rememberScrollState()
    var output by remember { mutableStateOf("") }
    val outputObserver = Observer<String>{
        output = it
    }
    appViewModel.outputTextLiveData.observe(lifecyleOwner, outputObserver)


    var telnetStatus by remember{ mutableIntStateOf(TelnetClient.STATUS_DISCONNECTED) }
    val statusObserver = Observer<Int>{
        telnetStatus = it
    }
    appViewModel.telnetConnectionStatus.observe(lifecyleOwner, statusObserver)



    Column (modifier = Modifier.padding(20.dp),) {
        when (telnetStatus ) {
            TelnetClient.STATUS_DISCONNECTED -> Text(text = "disconnected :(")
            TelnetClient.STATUS_CONNECTED -> Text(text = "connected :)")
            TelnetClient.STATUS_CONNECTING -> Text(text = "connecting...")
            TelnetClient.STATUS_CONNECTION_ERROR -> Text(text = "connection error.")
        }

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
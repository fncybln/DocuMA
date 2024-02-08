package com.theatretools.documa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.ui.theme.DocuMATheme

class AddPresetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RudimentaryDummyInput(callback = {presetItem, deviceInPreset ->  finishIntent(presetItem, deviceInPreset)})

                }
            }
        }
    }
    fun finishIntent(presetItem: PresetItem, deviceInPreset: DeviceInPreset){
        val replyIntent = Intent()
        replyIntent.putExtra("presetID", presetItem.presetID)
            .putExtra("presetName", presetItem.presetName)
            .putExtra("presetInfo", presetItem.presetInfo)
            .putExtra("devices",deviceInPreset.deviceId)
        setResult(RESULT_OK)
        finish()
    }
}


@Composable
fun RudimentaryDummyInput( callback : (presetItem: PresetItem, deviceInPreset: DeviceInPreset ) -> Unit ) {
    var presetItem by remember { mutableStateOf(PresetItem(null, 1, "name","info", "idk")) }
    var devInPre by remember { mutableStateOf(DeviceInPreset(null, null,1, "name")) }

    Column {
        TextField(
            value = presetItem.presetID.toString(),
            onValueChange = { if(it.isDigitsOnly()) presetItem.presetID = it.toIntOrNull() },
            label = { -> Text(text = AnnotatedString("Preset ID")) },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = presetItem.presetName.toString(),
            onValueChange = { presetItem.presetName = it },
            label = { -> Text(text = AnnotatedString("Preset name")) },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = presetItem.presetInfo.toString(),
            onValueChange = { presetItem.presetInfo = it },
            label = { -> Text(text = AnnotatedString("Info")) },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = devInPre.deviceId.toString(),
            onValueChange = { if(it.isDigitsOnly()) devInPre.deviceId = it.toIntOrNull() },
            label = { -> Text(text = AnnotatedString("Device of Preset")) },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        Button(onClick = { callback(presetItem, devInPre) }) {
            Text(text = AnnotatedString("Add Item"))
        }
    }
}

fun addPresetDummy(presetItem: PresetItem, deviceInPreset: DeviceInPreset){

}
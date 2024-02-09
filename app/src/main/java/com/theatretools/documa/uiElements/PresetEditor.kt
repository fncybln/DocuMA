package com.theatretools.documa.uiElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem

class PresetEditorConstants{
    companion object{
        public val EDIT = 1
        public val ADD = 2

    }
}

@Composable
fun PresetEditor(mode: Int, presetItem: PresetItem?, devInPre: List<DeviceInPreset>?, callback : (presetItem: PresetItem, devicesInPreset: List<DeviceInPreset>? ) -> Unit ) {
    var presetID by remember { mutableStateOf(1.toString()) }
    var presetName by remember { mutableStateOf("name") }
    var presetInfo by remember { mutableStateOf("info") }
    var deviceId by remember { mutableStateOf(1.toString()) }

    if (mode == PresetEditorConstants.EDIT){
        presetID = presetItem?.presetID.toString()
        presetName = presetItem?.presetName?:"name"
        presetInfo = presetItem?.presetInfo?:"info"
        deviceId = 1.toString()
    }

    fun returnCallback(){
        var returnPresetItem = PresetItem(presetItem?.id, presetID.toIntOrNull(), presetName, presetInfo, "idk")
        //TODO: Implement DeviceInPreset functionality
        var returnDevInPre = listOf(DeviceInPreset(null, null,1, "name"))
        callback(returnPresetItem, returnDevInPre)
    }




    Column {
        TextField(
            value = presetID.toString(),
            onValueChange = { it -> if(it.isDigitsOnly()) presetID = it },
            label = { -> Text(text = AnnotatedString("Preset ID")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number,),
        )

        TextField(
            value = presetName.toString(),
            onValueChange = { presetName = it },
            label = { -> Text(text = AnnotatedString("Preset name")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = presetInfo.toString(),
            onValueChange = { presetInfo = it },
            label = { -> Text(text = AnnotatedString("Info")) },
            maxLines = 2,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = deviceId.toString(),
            onValueChange = { if(it.isDigitsOnly()) deviceId = it },
            label = { -> Text(text = AnnotatedString("Device of Preset, ")) },
            maxLines = 1,
            //textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number,),
            enabled = false
        )
        IconButton( modifier = Modifier.align(Alignment.End).padding(20.dp),onClick = {
            returnCallback()
        }) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null)
            Text(text = AnnotatedString("OK"))
        }
    }
}
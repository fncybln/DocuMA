package com.theatretools.documa.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.theatretools.documa.MainApplication
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.ui.theme.DocuMATheme
import com.theatretools.documa.uiElements.PresetEditor

class AddPresetActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PresetEditor(null, null, callback = { presetItem, deviceInPreset ->  finishIntent(presetItem,
                        deviceInPreset?.get(0) ?: DeviceInPreset(null, null, null, null)
                    )})

                }
            }
        }
    }
    fun finishIntent(presetItem: PresetItem, deviceInPreset: DeviceInPreset){
        appViewModel.insertPreset(presetItem)
        val replyIntent = Intent()
        replyIntent.putExtra("presetID", presetItem.presetID)
        setResult(RESULT_OK)
        finish()
    }
}




fun addPresetDummy(presetItem: PresetItem, deviceInPreset: DeviceInPreset){

}

@Preview
@Composable
fun preview(){
    PresetEditor(null, null, callback = { a, b -> })
}
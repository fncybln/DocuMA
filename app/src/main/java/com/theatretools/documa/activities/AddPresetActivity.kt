package com.theatretools.documa.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.theatretools.documa.MainApplication
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.ui.theme.DocuMATheme
import com.theatretools.documa.uiElements.PresetEditor
import com.theatretools.documa.uiElements.PresetEditorConstants

class AddPresetActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }

    var newAllPictureUri: Uri? = null
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        newAllPictureUri = uri
        if (uri != null) {
            this.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
        Log.d("PhotoPicker", "No media selected")
    }
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
                    PresetEditor(PresetEditorConstants.ADD, null, devInPre = null, callback = { presetItem, deviceInPreset ->  finishIntent(presetItem,
                        deviceInPreset?.get(0) ?: DeviceInPreset(null, null, null, null)
                    )}, addImageCallback = {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    })

                }
            }
        }
    }
    fun finishIntent(presetItem: PresetItem, deviceInPreset: DeviceInPreset){
        presetItem.allPictureUri = newAllPictureUri.toString()
        appViewModel.insertPreset(presetItem)
        val replyIntent = Intent()
        replyIntent.putExtra("presetID", presetItem.presetID)
        setResult(RESULT_OK)
        finish()
    }
}

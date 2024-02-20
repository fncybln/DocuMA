package com.theatretools.documa.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.theatretools.documa.MainApplication
import com.theatretools.documa.activities.ui.theme.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.uiElements.ImportScreen
import com.theatretools.documa.xmlTools.readout
import kotlinx.coroutines.Job

class TestActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    var job: Job? = null
    val getXML = registerForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) {
        Log.v("ImportActivity", it.size.toString() + " Items")
        it.forEach{uri -> this.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)}
        readout(it, contentResolver)?.forEach{ item ->
            Log.v("ImportActivity", "${item.presetName} | ${item.presetIndex} - Import: \n" +
                    "DeviceList: ${item.deviceList} \n " +
                    "Showfile name: ${item.showfileName}\n" +
                    "preset name: ${item.presetName} \n" +
                    "${item.infoDate} / ${item.infoText}")
            job = item.toDatabase(appViewModel)
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
                    ImportScreen({
                        getXML.launch(arrayOf("*/*"))
                    }, null )
                    Log.e("importActivity", "ImportScreen")
                }
            }
        }
    }
}


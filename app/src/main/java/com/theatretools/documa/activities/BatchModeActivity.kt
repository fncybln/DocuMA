package com.theatretools.documa.activities

import android.content.Intent
import android.os.Bundle
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
import com.theatretools.documa.activities.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.uiElements.BatchModeScreen

class BatchModeActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }


    val  photoIntent = registerForActivityResult(contract = ActivityResultContracts.TakePicture()., {})


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BatchModeScreen(
                        modifier = Modifier,
                        onPhotoClick = ,
                        currentPreset = ,
                        backwardsFixtureID = ,
                        forwardsFixtureID = ,
                        currentFixtureID = ,
                        onPresetBackwards = ,
                        onPresetForwards =  ,
                        onFixtureBackwards = ,
                        onFixtureForwards = ,
                        onTelnetResend = ,
                        appViewModel = appViewModel,
                        lifecyleOwner = this)
                }
            }
        }
    }

    private fun startEditActivity(presetItem: PresetItem){
        val editIntent = Intent(applicationContext, EditPresetActivity::class.java).putExtra("editID", presetItem.id)
        editPreset.launch(editIntent)
    }

}

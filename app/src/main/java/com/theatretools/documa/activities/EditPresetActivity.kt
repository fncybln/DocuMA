package com.theatretools.documa.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.OnConflictStrategy
import com.theatretools.documa.MainApplication
import com.theatretools.documa.activities.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.uiElements.PresetEditor

class EditPresetActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentPresetID = intent.getIntExtra("editID", 0)
        var presetToEdit = appViewModel.getPresetByID(intentPresetID)

        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.e("test", presetToEdit.toString())
                    PresetEditor(presetToEdit, null) { a, _ ->
                        appViewModel.updatePreset(a) //TODO: Error handling
                        finish()
                    }
                }
            }
        }
    }
}


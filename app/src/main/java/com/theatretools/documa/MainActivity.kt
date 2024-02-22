package com.theatretools.documa

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
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import com.theatretools.documa.activities.AddPresetActivity
import com.theatretools.documa.activities.EditPresetActivity
import com.theatretools.documa.activities.ImportActivity
import com.theatretools.documa.activities.UtilitiesActivities
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.ui.theme.DocuMATheme
import com.theatretools.documa.uiElements.MainScreen

class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    val  addPreset = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { null }
    val  editPreset = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { null }
    val  importPreset = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult(), { null })
    val  utilitiesScreen = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult(), {null})

    var deviceList: List<Device>? = null
    val deviceObserver = Observer<List<Device>>{deviceList = it}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appViewModel.AllDeviceItems.observe(this, deviceObserver)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = appViewModel, lifecycleOwner = this, deviceList = deviceList,
                        {startAddActivity()},
                        {preset -> startEditActivity(preset)},
                        {startImportActivity()},
                        {utilitiesScreenActivity()}
                    )
                }
            }
        }
    }

    private fun utilitiesScreenActivity() {
        val utilIntent = Intent(applicationContext, UtilitiesActivities::class.java)
        utilitiesScreen.launch(utilIntent)
    }

    private fun startAddActivity(){
        val addIntent = Intent(applicationContext, AddPresetActivity::class.java)
        addPreset.launch(addIntent)
        Log.i( "tag",
            "intent launched: com.theatretools.documa.AddPresetActivity")
    }
    private fun startEditActivity(presetItem: PresetItem){
        val editIntent = Intent(applicationContext, EditPresetActivity::class.java).putExtra("editID", presetItem.id)
        editPreset.launch(editIntent)
    }

    private fun startImportActivity() {
//        var importIntent =Intent(applicationContext, ImportActivity::class.java)
//        Log.v("MainActivity", "importIntent")
//        importPreset.launch(importIntent)
        var importIntent = Intent(applicationContext, ImportActivity::class.java)//.putExtra("editID", 1) //TODO: Change class names
//        importPreset.launch(importIntent)
        startActivity(importIntent)
    }
}


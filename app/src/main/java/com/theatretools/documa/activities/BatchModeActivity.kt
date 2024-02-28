package com.theatretools.documa.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
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
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.uiElements.BatchModeScreen

class BatchModeActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }

    val  photoActivityResult = registerForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        {
        })
    private fun takePhoto(presetItem: PresetItem?, device: Device?){
        photoActivityResult.launch(Uri.parse("${MediaStore.Images.Media.EXTERNAL_CONTENT_URI}/DocuMa/0.${presetItem?.presetID?:"null"}/${device?.fix?:"null"}"))
    }


    // Database tools

    var OrderOfDeviceInPreset: List<DeviceInPreset>?  = null
    val lastPosition = appViewModel.getLastDevInPresetPosition()
    var currentPosition = lastPosition?:0
    var currentPreset = 0
    var currentFixture = 0


    private fun nextFixture(forward: Boolean = true ){
        when (forward) {
            true -> currentPosition++
            false -> currentPosition--
        }
        appViewModel.updateLastDevInPresetPosition(currentPosition)
        callCurrentTelnet()
    }
    private fun nextPreset(forward: Boolean = true) {
        var currentPreset = appViewModel.getPresetFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition))?.id
         do {
            when (forward) {
                true -> currentPosition++
                false -> currentPosition--
            }
        } while (currentPreset == appViewModel.getPresetFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition))?.id)
        appViewModel.updateLastDevInPresetPosition(currentPosition)
        callCurrentTelnet()
    }

    private fun callCurrentTelnet(){
        var fix = appViewModel.getDeviceFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition))?.fix
        var preset = appViewModel.getPresetFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition))?.presetID
        if(fix != null && preset != null ){
            appViewModel.telnetSendCmd("CLEARALL") {}
            appViewModel.telnetSendCmd("FIXTURE $fix AT PRESET 0.$preset") {}
        }


    }
    private fun callTelnetTools(){
        appViewModel.telnetSendCmd("CLEARALL"){}
        appViewModel.telnetSendCmd("HIGHLIGHT ON"){}
    }

    private fun callTelnetLogin() {
        appViewModel.telnetSendCmd(appViewModel.getLoginData()) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel.getDevInPresetOrdered {list, index ->
            OrderOfDeviceInPreset = list
            currentPosition = index
        }
        appViewModel.telnetConnect({},{})
        appViewModel.telnetCheckConnectivity()
        callTelnetLogin(); callTelnetTools(); callCurrentTelnet()

        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BatchModeScreen(
                        modifier = Modifier,
                        onPhotoClick = { takePhoto(
                            presetItem = appViewModel.getPresetFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition)),
                            device = appViewModel.getDeviceFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition))
                        ) },
                        currentPreset = appViewModel.getPresetFromDevInPreset(OrderOfDeviceInPreset?.get(currentPosition)),
                        backwardsFixtureID = try {appViewModel.getDeviceFromDevInPreset(
                            OrderOfDeviceInPreset?.get(currentPosition.apply{ this-1})
                        )?.fix.toString()} catch (e: IndexOutOfBoundsException) {"no more"},
                        forwardsFixtureID = try {appViewModel.getDeviceFromDevInPreset(
                            OrderOfDeviceInPreset?.get(currentPosition.apply{ this+1})
                        )?.fix.toString()} catch (e : IndexOutOfBoundsException) {"no more"},
                        currentFixtureID = appViewModel.getDeviceFromDevInPreset(
                            OrderOfDeviceInPreset?.get(currentPosition))?.fix.toString(),
                        onPresetBackwards = {nextPreset(false)},
                        onPresetForwards = {nextPreset(true)},
                        onFixtureBackwards = { nextFixture(false) },
                        onFixtureForwards = { nextFixture(true) },
                        onTelnetResend = {callTelnetLogin(); callTelnetTools(); callCurrentTelnet()},
                        appViewModel = appViewModel,
                        lifecyleOwner = this)
                }
            }
        }
    }






}

package com.theatretools.documa.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.theatretools.documa.MainApplication
import com.theatretools.documa.activities.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.uiElements.UtilitiesScreen

class UtilitiesActivities : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        var telnetIP = try {appViewModel.getTelnetIP()} catch (e: IndexOutOfBoundsException) {null}
        var showfileName = appViewModel.getShowfileName()
//        if (telnetIP != null) {
//            appViewModel.telnetClient.connect(telnetIP, null)
//        }
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UtilitiesScreen(Modifier.padding(20.dp), telnetIP, showfileName,
                        {
                            appViewModel.clearoutDatabase()
                        },
                        {
                            appViewModel.updateTelnetIP(it)

                        }, {
                            appViewModel.updateShowfileName(it)
                        })

                }
            }
        }
    }
}

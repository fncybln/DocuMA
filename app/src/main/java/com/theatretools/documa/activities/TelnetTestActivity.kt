package com.theatretools.documa.activities

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.theatretools.documa.MainApplication
import com.theatretools.documa.activities.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.telnet.TelnetClient
import com.theatretools.documa.uiElements.TelnetView
import kotlinx.coroutines.Job
import java.lang.IndexOutOfBoundsException
import java.net.SocketException

class TelnetTestActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var telnet = appViewModel.telnetClient
        appViewModel.telnetCheckConnectivity()
        var outputText : String? = appViewModel.outputText

        try {
            appViewModel.telnetConnect(
                onResult = {},
                onError = { e ->

//                    throw e
                }
            )
        }
        catch (e: SocketException) {
            Log.e("TelnetTestActivity", "${e.printStackTrace()}\n${e.toString()}")
            outputText = "Connection Failed. Stack trace: \n${e.printStackTrace()} \n $e"
        }

        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelnetView(appViewModel, this,
                        outputText,
                        sendAction = {
                            cmd ->
                             try {
                                cmd?.let{
                                    appViewModel.telnetGetResponse(it,
                                        onError = {outputText = it.toString()},
                                        onSuccess = {outputText = appViewModel.outputText},
                                        onNotConnected = {outputText = appViewModel.outputText})
                                }
                                } catch (e: IndexOutOfBoundsException) {
                                     outputText ="IndexOutOfBoundsException"
                                }
                        }, connectAction = {
                            Log.v("TelnetTestActivity", "Connection status: ${appViewModel.telnetConnectionStatus.value}")
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appViewModel.telnetClient.close()
    }
}


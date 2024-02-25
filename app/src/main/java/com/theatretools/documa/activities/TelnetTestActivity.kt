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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.theatretools.documa.MainApplication
import com.theatretools.documa.activities.ui.theme.DocuMATheme
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.telnet.TelnetClient
import com.theatretools.documa.uiElements.TelnetView
import java.lang.IndexOutOfBoundsException
import java.net.SocketException

class TelnetTestActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var telnet = appViewModel.telnetClient
        var outputText : String? = appViewModel.


        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelnetView(outputText,  {
                        cmd ->
                         try {
                            cmd?.let{
                                appViewModel.telnetGetResponse(it,
                                    onError = {outputText = it.toString()},
                                    onSuccess = {outputText = it
                                    Log.v("TelnetOutput", outputText?:"null")})
                            }
                        } catch (e: IndexOutOfBoundsException) {
                             outputText ="IndexOutOfBoundsException"
                        }
                    },
                    {
                        try {
                            appViewModel.telnetConnect(){msg, result, e -> if (!result) outputText = msg}}
                        catch (e: SocketException) {
                            Log.e("TelnetTestActivity", "${e.stackTrace}\n${e.toString()}")
                            outputText = "Connection Failed. Stack trace: \n${e.stackTrace} \n $e"
                        }
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


package com.theatretools.documa

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.theatretools.documa.ui.theme.DocuMATheme
import java.lang.NullPointerException

class ImportActivity : ComponentActivity() {
    lateinit var observer: AppLifecycleObserver
    companion object {
        val PICK_DOCTREE_REQ: Int = 1
    }
    var resultText: String? = "empty"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = AppLifecycleObserver(requireActivity().ActivityResultRegistry)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(onClick = { docActivityResult.launch(null)}) {
                        Text("Import aus individuellen Preset-Xmls")
                    }
                    Text(resultText?:"null")
                }
            }
        }
    }

    private val docActivityResult =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()){uri ->
            resultText = uri.toString()
            Log.i("Info","ActivityResult got called")
        }
    fun openDirectory(context: Context) {
        docActivityResult.launch(null)
    }

}





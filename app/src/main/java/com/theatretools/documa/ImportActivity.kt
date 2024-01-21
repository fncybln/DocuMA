package com.theatretools.documa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.startActivityForResult
import com.theatretools.documa.ui.theme.DocuMATheme

class ImportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    importButton()
                }
            }
        }
    }
}

@Composable
fun importButton() {
    Button(onClick = {}) {
        Text("Import aus individuellen Preset-Xmls")
    }
}

fun openDirectory(pickerInitialUri: Uri, requestcode: Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {}

}

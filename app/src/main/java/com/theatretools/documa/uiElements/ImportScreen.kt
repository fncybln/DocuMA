package com.theatretools.documa.uiElements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job

@Composable
fun ImportScreen( importClick: () -> Unit, importJob: Job?) {
    Row {

        Button (onClick = {importClick()}) {

            Row {
                Icon(Icons.Filled.Build, contentDescription = null)
                Text(AnnotatedString("Import from MA Export"))
            }
        }

        if (importJob != null) {
            if (importJob.isActive) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            if (importJob.isCompleted) {
                Text("Success!")
            }
        }
    }
}

@Composable
@Preview
fun prev() {
    ImportScreen(importClick = { /*TODO*/ }, importJob = Job())
}

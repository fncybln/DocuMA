@file:OptIn(ExperimentalMaterial3Api::class,
)

package com.theatretools.documa.uiElements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.telnet.TelnetClient

var paddingLessText = TextStyle(
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)

@Composable
fun BatchModeScreen(
    modifier: Modifier = Modifier,
    onPhotoClick: () -> Unit,
    currentPreset: PresetItem,
    backwardsFixtureID: String,
    forwardsFixtureID: String,
    currentFixtureID: String,
    onPresetBackwards: ()-> Unit,
    onPresetForwards: ()-> Unit,
    onFixtureBackwards: ()-> Unit,
    onFixtureForwards: ()-> Unit,
    onTelnetResend: ()-> Unit,
    appViewModel: AppViewModel,
    lifecyleOwner: LifecycleOwner
    ) {
    var telnetStatus by remember{ mutableIntStateOf(TelnetClient.STATUS_DISCONNECTED) }
    val statusObserver = Observer<Int>{
        telnetStatus = it
    }
    appViewModel.telnetConnectionStatus.observe(lifecyleOwner, statusObserver)

    Box(modifier = modifier
        .fillMaxSize()
        .padding(20.dp),){
        Box (modifier = modifier.align(Alignment.BottomCenter)){
            TakePhotoButton(Modifier.align(Alignment.BottomEnd), onClick = { onPhotoClick })
        }
        Box (modifier = modifier.fillMaxWidth(1F)) {
            Column (modifier.align(Alignment.TopCenter)) {
                PresetDisplay(presetID = currentPreset.presetID.toString(), presetName = currentPreset.presetName?:"null")
                Row (
                    modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()){
                    PresNav(
                        modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3F),
                        direction = false,
                        onClick = { onPresetBackwards })
//                    Spacer(modifier = Modifier.width(10.dp))
                    PresNav(
                        modifier
                            .fillMaxHeight()
                            .fillMaxWidth(1F),
                        direction = true,
                        onClick = {onPresetForwards})
                }
                Row (
                    modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()){
                    FixNav(
                        Modifier
                            .fillMaxHeight(1F)
                            .width(80.dp), false, backwardsFixtureID
                    ) { onFixtureBackwards }
                    FixNav(
                        Modifier
                            .fillMaxHeight(1F)
                            .width(80.dp), true, forwardsFixtureID
                    ) { onFixtureForwards }
                    FixDisplay(
                        Modifier
                            .fillMaxHeight(1F)
                            .fillMaxWidth(),
                        currentFixtureID
                    )
                }
                Row (
                    modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()){

                    TelnetStatusCard(
                        Modifier
                            .fillMaxHeight(1F)
                            .fillMaxWidth(0.2F),
                        status = telnetStatus
                        )
                    ResendTelnetCard(
                        Modifier
                            .fillMaxHeight(1F)
                            .fillMaxWidth(1F),
                        onClick = onTelnetResend)
                }

            }

        }
    }

}

@Composable
fun FixNav(modifier: Modifier = Modifier, direction: Boolean, fixNumber: String, onClick: () -> Unit  ) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),onClick = { onClick },
        modifier = modifier.padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = MaterialTheme.colorScheme.surface,
        )) {

        Box(modifier.padding(10.dp)) {

            when (direction) {
                true -> Icon(
                    Icons.Filled.KeyboardArrowRight,
                    "",
                    modifier
                        .align(
                            Alignment.Center
                        )
                        .size(30.dp)
                )
                false -> Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    "",
                    modifier
                        .align(
                            Alignment.Center
                        )
                        .size(30.dp)
                )
            }
            Text(
                    text = fixNumber.toString(),
            style = MaterialTheme.typography.titleSmall.merge(paddingLessText),
            modifier = modifier.align(
                Alignment.BottomStart
            ), color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun PresNav(modifier: Modifier = Modifier, direction: Boolean, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),onClick = { onClick },
        modifier = modifier
            .padding(5.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.surface,
        )) {
        Icon(
                when (direction) {false -> Icons.Filled.KeyboardArrowLeft
                                 else -> Icons.Filled.KeyboardArrowRight}
                ,
                "",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .size(30.dp),
                tint  = MaterialTheme.colorScheme.surface,
            )
    }
}

@Composable
fun FixDisplay(modifier: Modifier = Modifier, fixtureID: String) {
    Card(
        onClick = {}, modifier
            .padding(5.dp)
            .fillMaxHeight(1F),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = MaterialTheme.colorScheme.surface,
        )) {
        Column (
            modifier
                .padding(20.dp)
                .fillMaxHeight(1F)){
            Text(
                text = AnnotatedString(fixtureID.toString()),
                modifier = Modifier,
                style = MaterialTheme.typography.displaySmall.merge(paddingLessText),
//                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = AnnotatedString("Current Fixture"),
                modifier = modifier,
                style = MaterialTheme.typography.labelSmall.merge(paddingLessText),
//                color = MaterialTheme.colorScheme.onSecondary
            )




        }
    }
    
}

@Composable
fun PresetDisplay(modifier: Modifier = Modifier, presetID: String, presetName: String) {
    Card(
        onClick = {  },
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(1F),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    )
    {
        Column(modifier.padding(20.dp)) {
            Text(
                text = AnnotatedString("Current Preset:"),
                modifier = modifier,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Text(
                    text = AnnotatedString(presetID),
                    modifier = Modifier,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = AnnotatedString(presetName),
                    maxLines = 2,
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}

@Composable
fun TelnetStatusCard(modifier: Modifier = Modifier, status: Int? = TelnetClient.STATUS_CONNECTED) {
    when (status) {
        TelnetClient.STATUS_CONNECTED ->
            Card(onClick = { /**/ },
                modifier = modifier.padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary,
                )) {
                Box (
                    modifier
                        .padding(20.dp)
                        .align(Alignment.End)) {
                    Icon(Icons.Filled.Done, "", modifier.align(Alignment.Center))
                }
            }
        TelnetClient.STATUS_DISCONNECTED  ->
            Card(onClick = { /**/ },
                modifier = modifier.padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                )) {
                Column (
                    modifier
                        .padding(20.dp)
                        .align(Alignment.End)) {
                    Text(
                        text = "Telnet",
                        style = MaterialTheme.typography.labelSmall
                            .merge(paddingLessText),
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Icon(Icons.Filled.Clear, "", modifier.align(Alignment.CenterHorizontally))
                }
            }
        TelnetClient.STATUS_CONNECTING ->
            Card(onClick = { /**/ },
                modifier = modifier.padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary,
                )) {
                Column (
                    modifier
                        .padding(20.dp)
                        .align(Alignment.End)) {
                    Text(
                        text = "Telnet",
                        style = MaterialTheme.typography.labelSmall
                            .merge(paddingLessText),
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    CircularProgressIndicator(modifier.align(Alignment.CenterHorizontally), color = MaterialTheme.colorScheme.tertiary)
                }
            }
        TelnetClient.STATUS_CONNECTION_ERROR ->
            Card(onClick = { /**/ },
                modifier = modifier.padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  MaterialTheme.colorScheme.onErrorContainer,
                    contentColor = MaterialTheme.colorScheme.secondary,
                )) {
                Column (
                    modifier
                        .padding(20.dp)
                        .align(Alignment.End)) {
                    Text(
                        text = "Telnet",
                        style = MaterialTheme.typography.labelSmall
                            .merge(paddingLessText),
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Icon(Icons.Filled.Clear, "", modifier.align(Alignment.CenterHorizontally))
                }
            }
    }

}

@Composable
fun ResendTelnetCard(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),onClick = { onClick },
        modifier = modifier.padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = MaterialTheme.colorScheme.surface,
        )) {
        Column (
            modifier
                .padding(20.dp)
                .align(Alignment.End)) {
            Text(
                text = "Resend Telnet",
                style = MaterialTheme.typography.titleMedium.merge(paddingLessText),
                )
        }
    }
}

@Composable
fun TakePhotoButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),onClick = { onClick },
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.surface,
        )){
        Row (modifier = modifier.padding(20.dp)){
            Icon(Icons.Filled.ExitToApp, null, modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Take Picture", modifier.align(Alignment.CenterVertically), style = MaterialTheme.typography.titleMedium)
        }
    }
}





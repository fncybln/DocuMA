package com.theatretools.documa.uiElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.theatretools.documa.R
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.DataRepository
import com.theatretools.documa.dataobjects.Device



@Composable
fun DeviceSelector(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    lifecycleOwner: LifecycleOwner,

    ) {
    var list: List<Device>? = null
    val deviceObserver = Observer<List<Device>>{list = it}
    val data = viewModel.AllDeviceItems.observe(lifecycleOwner, deviceObserver)


    LazyRow( horizontalArrangement = Arrangement.spacedBy (8.dp),
        modifier = Modifier
    ){
        if (list != null) {
            for(device in list!!) {
                item (key = device.id) {
                    DeviceItem(
                        device, repository = null, modifier = null
                    ) { //TODO: ClickAction
                    }

                }
            }

        }
    }



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceItem(device: Device, repository: DataRepository?, modifier: Modifier?, action: ()->Unit){
    Card(onClick = action, modifier = Modifier.width(180.dp)) {
        Row (
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(8.dp)) {

            Column(modifier = Modifier.padding(top = 8.dp)){
                Image(
                    painter = painterResource(R.drawable.dummy_deviceimg), //TODO
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .requiredSize(60.dp, 50.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                Text(
                    text = AnnotatedString("PATCH"), //TODO
                    style = MaterialTheme.typography.bodyLarge
                )


            }
            Column (modifier = Modifier.padding(start = 8.dp)){
                Text(text = AnnotatedString( device.getFixChan()), //TODO: ID
                    style = MaterialTheme.typography.titleLarge,
                    )
                Text(
                    modifier = Modifier.size(90.dp, 50.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                    text = AnnotatedString("name"),
                    )
            }
        }

    }
}

package com.theatretools.documa.uiElements

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.theatretools.documa.R
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem

@Composable
fun PresetSelector(modifier: Modifier, viewModel: AppViewModel, lifecycleOwner: LifecycleOwner, action :(presetItem: PresetItem) -> Unit) {

    var list: List<PresetItem>? = null
    val presetObserver = Observer<List<PresetItem>>{list = it}
    viewModel.AllPresetItems.observe(lifecycleOwner, presetObserver) //????

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement =  Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        if (list != null) {
            for(preset in list!!) {
                item (key = preset.id) {
                    PresetSelectionItem(
                        preset, action

                    )

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetSelectionItem(presetItem: PresetItem, action: (presetItem: PresetItem)->Unit) {
    Card(onClick = { action(presetItem) },
        modifier = Modifier.width(180.dp)
    ) {
        Column {
            if (presetItem.allPictureUri != null) {
                var imgReq = ImageRequest.Builder(LocalContext.current)
                    .data(/*"android.resource://com.theatretools.documa/"+R.drawable.dummy_preset_pic*/presetItem.allPictureUri)
                    .placeholder(R.drawable.picnotfound) //TODO: load image
                    .error(R.drawable.dummy_deviceimg)
                    .listener(onError = {a, errorResult -> Log.e("ImageRequest", errorResult.throwable.toString())})
                    .build()
                AsyncImage(model = imgReq,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(80.dp)
                )
                /*
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(presetItem.allPictureUri)),
                    contentDescription = null
                )*/
            } else {

                Text(
                    AnnotatedString("No Image"),
                    modifier = Modifier.padding(20.dp),
                    color = Color.LightGray,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge


                )
            }
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp, top = 8.dp, end = 8.dp)
                    .fillMaxWidth(1f)
            ) {
                Row (horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier){
                    Text(
                        text = AnnotatedString(presetItem.presetID.toString()),
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = AnnotatedString(presetItem.presetName?:"no preset name"),
                        maxLines = 1,
                        modifier = Modifier,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
//                Text(text =AnnotatedString( "1"),
//                    style = MaterialTheme.typography.labelSmall
//                )
// TODO: Preset Type?
            }
        }
    }
}
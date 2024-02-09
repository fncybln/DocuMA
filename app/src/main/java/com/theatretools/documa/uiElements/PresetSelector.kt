package com.theatretools.documa.uiElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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
            Image(
                painter = painterResource(id = R.drawable.dummy_preset_pic/*TODO*/),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(80.dp)
            )
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
                Text(text =AnnotatedString( "1"),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
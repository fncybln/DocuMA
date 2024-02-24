package com.theatretools.documa.uiElements

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.launch


class NavDrawItem (
    var name: String,
    var icon: ImageVector,
    var content: () -> Unit
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AppViewModel, lifecycleOwner: LifecycleOwner,
    deviceList: List<Device>?,
    addButtonAction: ()-> Unit,
    editCardAction: (presetItem: PresetItem)-> Unit,
    importButtonAction: ()-> Unit,
    utilitiesButtonAction: () -> Unit,
    telnetButtonAction: () -> Unit
) {


    val scaffoldState = rememberBottomSheetScaffoldState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column (
            )
            {
                DeviceSelector(modifier = Modifier, deviceList)
                Text("test")
                Spacer(Modifier.height(8.dp))
                Row (modifier = Modifier.horizontalScroll(scrollState)){
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                        Icon(Icons.Filled.Home, contentDescription = null)
                        Text(text = AnnotatedString("All Presets"))
                    }
                    OutlinedButton(onClick = addButtonAction , modifier = Modifier.padding(8.dp)) {
                        Icon(Icons.Filled.Create, contentDescription = null)
                        Text(text = AnnotatedString("Add"))
                    }
                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                        Text(text = AnnotatedString("Show Plan"))
                    }
                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                        Icon(Icons.Default.List, contentDescription = null)
                        Text(text = AnnotatedString("Show Groups"))
                    }
                }
            }

        }) { _ ->


        val navDrawItems = listOf (
            NavDrawItem("Overview", Icons.Default.Home, {null}),
            NavDrawItem("Import", Icons.Default.Add, {importButtonAction()}),
            NavDrawItem("Utilities", Icons.Default.Create, {utilitiesButtonAction()}),
            NavDrawItem("TelnetTest", Icons.Default.ExitToApp, {telnetButtonAction()})
        )
        val selectedItem = remember { mutableStateOf(navDrawItems[0]) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    navDrawItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(AnnotatedString(item.name)) },
                            selected = item == selectedItem.value,
                            onClick =  {
                                Log.v("MainScreen", "onClick DrawerItem")
                                selectedItem.value = item
                                item.content()
                                scope.launch { drawerState.close() }

                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    } }
            },
        ) {
            Scaffold(
                modifier = Modifier.padding(8.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(AnnotatedString("")) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = null)
                            }
                        }
                    )
                },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            scope.launch { scaffoldState.bottomSheetState.expand()}
                        }
                    ) {
                        Text(AnnotatedString("Select Fixture"))
                        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "")
                    }
                },
                content = { padding ->
                    Text ("test")
                    Box(/* TODO: refresh pull */) {
                        PresetSelector(
                            modifier = Modifier.padding(padding),
                            viewModel = viewModel,
                            lifecycleOwner = lifecycleOwner,
                            action = editCardAction
                        )
                    }
                }
            )
        }
    }
}

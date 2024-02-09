package com.theatretools.documa

import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.theatretools.documa.activities.AddPresetActivity
import com.theatretools.documa.activities.EditPresetActivity
import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.data.ViewModelFactory
import com.theatretools.documa.dataobjects.PresetItem
import com.theatretools.documa.ui.theme.DocuMATheme
import com.theatretools.documa.uiElements.DeviceSelector
import com.theatretools.documa.uiElements.PresetSelector
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory((application as MainApplication).repository)
    }
    val  addPreset = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { null }
    val  editPreset = registerForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { null }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocuMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = appViewModel, lifecycleOwner = this, {startAddActivity()}, {preset -> startEditActivity(preset)})
                }
            }
        }
    }
    fun startAddActivity(){
        var addIntent = Intent(applicationContext, AddPresetActivity::class.java)
        addPreset.launch(addIntent)
        Log.i( "tag",
            "intent launched: com.theatretools.documa.AddPresetActivity")
    }
    fun startEditActivity(presetItem: PresetItem){
        var editIntent = Intent(applicationContext, EditPresetActivity::class.java).putExtra("editID", presetItem.id)
        editPreset.launch(editIntent)

    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AppViewModel, lifecycleOwner: LifecycleOwner,
    addButtonAction: ()-> Unit,
    editCardAction: (presetItem: PresetItem)-> Unit,
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
                DeviceSelector(modifier = Modifier, viewModel, lifecycleOwner)
                //Spacer(Modifier.height(8.dp))
                Row (modifier = Modifier.horizontalScroll(scrollState)){
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

        val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
        val selectedItem = remember { mutableStateOf(items[0]) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    //Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item, contentDescription = null) },
                            label = { Text(AnnotatedString(item.name)) },
                            selected = item == selectedItem.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
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

package com.theatretools.documa.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.theatretools.documa.dataobjects.PresetItem
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppViewModel(private val repository: DataRepository): ViewModel() {

    val AllPresetItems: LiveData<List<PresetItem>> = repository.loadAllPresetItems().asLiveData()


}

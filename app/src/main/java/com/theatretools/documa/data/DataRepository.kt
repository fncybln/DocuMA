package com.theatretools.documa.data

import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.flow.Flow

class DataRepository(private val appDAO: AppDAO) {
    fun loadAllPresetItems(): Flow<List<PresetItem>> {
        return appDAO.loadAllPresetItems()
    }

    fun loadAllDeviceItems(): Flow<List<Device>> {
        return appDAO.loadAllDevices()

    }
}
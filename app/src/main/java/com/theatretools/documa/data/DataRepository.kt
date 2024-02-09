package com.theatretools.documa.data

import androidx.annotation.WorkerThread
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class DataRepository(private val appDAO: AppDAO) {
    fun loadAllPresetItems(): Flow<List<PresetItem>> {
        return appDAO.loadAllPresetItems()
    }

    fun loadAllDeviceItems(): Flow<List<Device>> {
        return appDAO.loadAllDevices()
    }

        @WorkerThread
    fun getPresetByID(id: Int): PresetItem{
        return appDAO.getPresetItemByID(id)
    }

    @WorkerThread
    fun updatePreset(preset: PresetItem):Int{
        return appDAO.updatePreset(preset)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPreset( preset: PresetItem) {
        appDAO.insertPreset(preset)
    }
}
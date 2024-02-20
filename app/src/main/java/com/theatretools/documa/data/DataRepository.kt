package com.theatretools.documa.data

import androidx.annotation.WorkerThread
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
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
    fun getDevice ( chan : Int , fix : Int ): Int?{
        return appDAO.getDeviceWithSameChanAndFix(fix, chan)?.id
    }
    @WorkerThread
    fun getDevice ( id: Int){
        appDAO.getDeviceByID(id)
    }

    @WorkerThread
    fun updatePreset(preset: PresetItem):Int{
        return appDAO.updatePreset(preset)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPreset( preset: PresetItem) : Long{
        return appDAO.insertPreset(preset)[0]
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUniqueDevice( device: Device ): Long {
        return if (appDAO.getDeviceWithSameChanAndFix(device.fix, device.chan) == null){
            appDAO.insertDevice(device)[0]
        } else 0
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDeviceInPreset( deviceInPreset: DeviceInPreset) {
        appDAO.insertDeviceInPreset(deviceInPreset)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearoutDatabase() {
        appDAO.clearoutDevice()
        appDAO.clearoutPresets()
        appDAO.clearoutDevInPreset()
    }
}
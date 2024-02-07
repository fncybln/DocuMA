package com.theatretools.documa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {
    @Query("SELECT * FROM Preset_Items")
    fun loadAllPresetItems(): Flow<List<PresetItem>>

    //Not necessary
    @Query("SELECT * FROM DeviceInPreset")
    suspend fun loadAllDeviceInPreset(): Flow<List<DeviceInPreset>>

    @Query("SELECT * FROM Device")
    suspend fun loadAllDevices(): Flow<List<Device>>

    @Query("SELECT * FROM Preset_Items WHERE (SELECT presetId FROM DeviceInPreset WHERE deviceId = :id)")
    suspend fun loadAllPresetsWhereDeviceId(id: Int): Flow<List<PresetItem>>

    @Query("SELECT * FROM Device WHERE (SELECT deviceId FROM DeviceInPreset WHERE deviceId = :id)")
    suspend fun loadAllDevicesWherePresetId(id: Int): Flow<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(vararg preset: PresetItem)
    @Delete
    suspend fun deletePreset(vararg preset: PresetItem)

}
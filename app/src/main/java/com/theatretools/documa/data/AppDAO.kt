package com.theatretools.documa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.Preferences
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.flow.Flow
import javax.crypto.AEADBadTagException

@Dao
interface AppDAO {
    @Query("SELECT * FROM Preset_Items ORDER BY presetID ASC")
    fun loadAllPresetItems(): Flow<List<PresetItem>>

    //Not necessary
    @Query("SELECT * FROM DeviceInPreset")
    fun loadAllDeviceInPreset(): Flow<List<DeviceInPreset>>

    @Query("SELECT * FROM Device ORDER BY fix ASC")
    fun loadAllDevices(): Flow<List<Device>>

    @Query("SELECT * FROM Preset_Items WHERE (SELECT presetId FROM DeviceInPreset WHERE deviceId = :id)")
    fun loadAllPresetsWhereDeviceId(id: Int): Flow<List<PresetItem>>

    @Query("SELECT * FROM Device WHERE (SELECT deviceId FROM DeviceInPreset WHERE deviceId = :id)")
    fun loadAllDevicesWherePresetId(id: Int): Flow<List<Device>>

    @Query("SELECT * FROM Device WHERE fix = :fix AND chan = :chan LIMIT 1")
    fun getDeviceWithSameChanAndFix(fix: Int?, chan: Int?): Device?

    @Query("SELECT DISTINCT fix, chan FROM Device WHERE fix = :fix AND chan = :chan LIMIT 1")
    fun getDeviceWithSameChanAndFixByDistinct(fix: Int?, chan: Int? ): Device?

    @Query("SELECT * FROM Device WHERE id = :id LIMIT 1")
    fun getDeviceByID(id: Int): Device?

    @Query("SELECT * FROM preset_items WHERE id = :id LIMIT 1")
    fun getPresetItemByID(id: Int): PresetItem

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updatePreset(presetItem: PresetItem): Int

    // INSERTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(vararg preset: PresetItem): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(vararg device: Device): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceInPreset(vararg deviceInPreset: DeviceInPreset): List<Long>
    @Delete
    suspend fun deletePreset(vararg preset: PresetItem)

    @Query ("DELETE FROM Preset_Items")
    suspend fun clearoutPresets()
    @Query ("DELETE FROM Device")
    suspend fun clearoutDevice()
    @Query ("DELETE FROM DeviceInPreset")
    suspend fun clearoutDevInPreset()

    @Query ("SELECT * FROM Preferences WHERE tag = :tag LIMIT 1")
    fun getPreference(tag: String): List<Preferences?>?

    @Query ("UPDATE Preferences SET content = :content WHERE tag = :tag")
    fun setPreference(tag: String, content: String)

    @Query ("INSERT INTO Preferences (tag, content) VALUES (:tag, :content)")
    fun insertPreference(tag: String, content: String)

}
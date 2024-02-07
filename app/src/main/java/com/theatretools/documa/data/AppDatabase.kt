package com.theatretools.documa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


//TODO: ExportSchema
@Database(entities = [PresetItem::class, Device::class, DeviceInPreset::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO

    companion object{
        @Volatile private var INSTANCE: AppDatabase?=null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context:Context):AppDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "App.db")
                .build()
    }
}
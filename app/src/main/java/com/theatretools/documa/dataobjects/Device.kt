package com.theatretools.documa.dataobjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Device (
    @PrimaryKey (autoGenerate = true) val id: Int?,
    var fix: Int?,
    var chan: Int?,
    var deviceType: Int?,
    var deviceName: String?
)
{
    fun getFixChan(): String {//TODO: Welche reihenfolge hat die fix:chan anzeige?
        return if(fix == chan) fix.toString()
        else "${fix?:"-"} : ${chan?:"-"}"
    }
}
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
}
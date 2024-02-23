package com.theatretools.documa.dataobjects

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class DeviceInPreset(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var presetId: Int?,
    var deviceId: Int?,

    var picName: String?,
) {
    init{

    }



}
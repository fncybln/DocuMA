package com.theatretools.documa.dataobjects

import android.os.Parcel
import android.os.Parcelable

class PresetItemPackage(
    val presetItem: PresetItem,
    val devices: List<Device>
) {}

data class DevicePackage(
    val device: Device,
    val presets: List<PresetItem>
)

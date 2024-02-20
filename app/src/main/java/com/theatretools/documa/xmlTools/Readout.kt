package com.theatretools.documa.xmlTools

import com.theatretools.documa.data.AppViewModel
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.Job

open class Readout {
    var presetName: String? = null
    var presetIndex: Int? = null
    var showfileName: String? = null
    var readoutTime: String? = null
    var infoDate: String? = null
    var infoText: String? = null
    var deviceList: List<Device?>? = null



    fun toDatabase(appViewModel: AppViewModel): Job?{
        val preset = PresetItem(null, presetIndex, presetName, infoText, null, infoDate, readoutTime)
        deviceList?.let { return appViewModel.insertPresetAndReferences(preset, it) }
        return null
    }
}
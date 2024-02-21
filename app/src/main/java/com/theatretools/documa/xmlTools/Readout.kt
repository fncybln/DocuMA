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



    fun toDatabase(appViewModel: AppViewModel, parentJob: Job): Job?{

        // Creates a Preset Item from the class variables and inserts it and the fixtures into the database.

        val preset = PresetItem(null, presetIndex, presetName, infoText, null, infoDate, readoutTime)
        deviceList?.let { return appViewModel.insertPresetAndReferences(preset, it, parentJob) }
        return null
    }
}
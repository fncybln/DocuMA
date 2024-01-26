package com.theatretools.documa.dataobjects

import android.net.Uri

class PresetItem (
    id: Int?,
    presetID: Int?,
    presetName: String?,
    presetInfo: String?,
    allPictureName: String?,
    presetContent: List<FixInPreset>?,
    idHandler: IdHandler
){
    init {
        var ID = id
        if (ID == null){
            ID = idHandler.newId()
        }
        if (!idHandler.checkIfIdIsUnique(ID)){
            throw Exception("UniqueIDException")
        }
        var item: FixInPreset
        if (presetContent != null) {
            for (item in presetContent){
                if (item.id == null
            }
        }
    }


    fun newchange(){}
    //TODO: Any changes must be updated via the updater!

}


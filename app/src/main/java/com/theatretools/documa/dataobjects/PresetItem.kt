package com.theatretools.documa.dataobjects

import android.net.Uri

class PresetItem (
    id: Int?,
    var presetID: Int?,
    var presetName: String?,
    var presetInfo: String?,
    var allPictureName: String?,
    var presetContent: List<FixInPreset>?,
    var idHandler: IdHandler
){
    var iD: Int? = null

    init {
        var iD = id
        if (iD == null){
            iD = idHandler.newId()
        }
        if (!idHandler.checkIfIdIsUnique(iD)){
            throw Exception("UniqueIDException")
        }
        var item: FixInPreset
        if (presetContent != null) {
            for (item in presetContent!!){
                if (item.id == null){}
            }
        }
    }

    fun PresetItem?.toString():String{
        return if (this!= null){
            "null"
        } else "ID: $iD | PrsID: $presetID | Name:$presetName \n Info: $presetInfo \n AllPictureName: $allPictureName \n PresetContent: \n${presetContent.toString()}"
    }
    fun newchange(){}
    //TODO: Any changes must be updated via the updater!

}


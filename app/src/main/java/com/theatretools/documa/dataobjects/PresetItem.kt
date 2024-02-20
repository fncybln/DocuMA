package com.theatretools.documa.dataobjects

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Preset_Items",
)
class PresetItem (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    var presetID: Int?,
    var presetName: String?,
    var presetInfo: String?,
    var allPictureUri: String?,
    var infoDate: String? = "",
    var readoutTimestamp: String? = ""

    //var presetContent: List<FixInPreset>?,
    //TODO: PresetType?
){


    init {

    }

    override fun toString():String{
        return "ID: $id | PrsID: $presetID | Name:$presetName \n Info: $presetInfo \n AllPictureName: $allPictureUri"
    }

}


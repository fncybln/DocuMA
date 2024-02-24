package com.theatretools.documa.dataobjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Preferences(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var tag: String,
    var content: String?,
    ){

}
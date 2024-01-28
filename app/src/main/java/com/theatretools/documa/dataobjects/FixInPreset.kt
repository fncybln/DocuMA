package com.theatretools.documa.dataobjects

class FixInPreset(
    ID: Int?,
    FIX: Int?,
    CHAN: Int?,
    inPicName: String?, //TODO: what kind of object to use for picture reference??
    idHandler: IdHandler
) {
    var id = ID
    var fix = FIX
    var chan = CHAN
    var picName = inPicName
    init{
        if (id == null){
            id = idHandler.newId()
        }
        else {
            if (!idHandler.checkIfIdIsUnique(id = id!!)){
                id= idHandler.newId()

            }
        }
    }

    fun FixInPreset?.toString(): String{
        return if (this != null) {"null"} else{
            "ID: $id | fix: $fix | chan: $chan | picName: $picName"
        }
    }


}
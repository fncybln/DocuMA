package com.theatretools.documa.dataobjects

class FixInPreset(
    ID: Int?,
    FIX: Int?,
    CHAN: Int?,
    inPicName: String? //TODO: what kind of object to use for picture reference??
    idHandler: IdHandler
) {
    private var id = ID
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


}
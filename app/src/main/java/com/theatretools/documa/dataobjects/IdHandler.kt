package com.theatretools.documa.dataobjects
class IdHandler{
    var idList = mutableListOf<Int>()
    fun newId(): Int{
        var id = getEmptyID()
        idList.add(id)
        return id
    }
    fun checkIfIdIsUnique(id: Int): Boolean{
        return(id in idList)
    }
    private fun getEmptyID(): Int{
        idList.sort()
        return idList[idList.size+1]+1
    }
}

package com.theatretools.documa.xmlTools

import android.net.Uri
import com.theatretools.documa.dataobjects.IdHandler
import com.theatretools.documa.dataobjects.PresetItem
import java.io.InputStream


// repository class for parsing .maExportCompressed.xml XML file and turn them into Data Objects.
// Should be deprecated soon and shifted to server side when server support
class MaImporter(var uri: Uri, var idHandler: IdHandler) {



    fun parseXml(filename: Uri): Result<List<PresetItem>>{
        val result: Result<List<PresetItem>>
        var presetReadout = PresetReadout(idHandler)

        presetReadout.parse(openInputStream())
    }

}
package com.theatretools.documa.xmlTools

import android.net.Uri
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream


// repository class for parsing .maExportCompressed.xml XML file and turn them into Data Objects.
// Should be deprecated soon and shifted to server side when server support
class MaImporter(var uri: Uri) {



    suspend fun parseXml():List<PresetItem>{
        val result: Result<List<PresetItem>>
        var presetReadout = PresetReadout()

        return withContext(Dispatchers.IO) {
            presetReadout.parse(FileInputStream(uri.toString()))
        }
    }

}
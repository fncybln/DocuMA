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
        var precompiledPresetReadout = PrecompiledPresetReadout()

        return withContext(Dispatchers.IO) {
            precompiledPresetReadout.parse(FileInputStream(uri.toString()))
        }
    }

}
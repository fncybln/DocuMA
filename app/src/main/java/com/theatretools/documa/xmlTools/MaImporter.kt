package com.theatretools.documa.xmlTools

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

class MaImporter (uri: Uri, ) {
    init {

    }

    suspend fun parseXml(context: Context, returnedIntent: Intent): Result<List<PresetItem>>{
        val result: Result<List<PresetItem>>
        return withContext(Dispatchers.IO){
            returnedIntent
        }
    }

}
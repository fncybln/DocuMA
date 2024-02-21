package com.theatretools.documa.xmlTools

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import android.util.Xml
import com.theatretools.documa.dataobjects.Device
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.IOException
import java.io.InputStream



// ==========================================================================

fun readout(uri: Uri) : List <MaExportReadout>?{
    val readout = mutableListOf<MaExportReadout>()
    val directory = File(uri.toString())
    val files = directory.listFiles()
    if (files != null) {
        Log.d("MaExportReadout", ("Size: " + files.size))
        for (i in files.indices) {
            var item = MaExportReadout()
            Log.d("Files", "FileName:" + files[i].name)
            item.parse(files[i].inputStream())
            readout.add(item)
        }
        return readout
    }
    else Log.d ("MaExportReadout", ("Size: null") ); return null
}


fun readout(uris: List<Uri>, contentResolver: ContentResolver) : MutableList<ReadoutMaExport>? {
    val readout = mutableListOf<ReadoutMaExport>()
    if (uris != null) {
        Log.d("MaExportReadout", ("Size: " + uris.size))
        for (i in uris.indices) {
            var item = ReadoutMaExport()
            Log.d("Files", "FileName:" + uris[i].toString())
            contentResolver.openInputStream(uris[i])?.let { item.parse(it) }
            readout.add(item)
        }
        return readout
    }
    else Log.d ("MaExportReadout", ("Size: null") ); return null
}


// ==========================================================================

class ReadoutMaExport  : Readout(){

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream){
        inputStream.use {stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)

            readPresetFile(parser)
        }
    }

    private fun readPresetFile(parser: XmlPullParser) {
        var devices: List<Device>? = listOfNotNull()
        var cond = false

        // Document Header

        parser.next()
        Log.v(this::class.toString(), parser.positionDescription)
        if (parser.getAttributeValue("", "xmlns") == "http://schemas.malighting.de/grandma2/xml/MA") {
            Log.v(this::class.toString(), "xmlns scheme = http://schemas.malighting.de/grandma2/xml/MA")
        } else throw Exception ("xmlns scheme should be xmlns scheme = http://schemas.malighting.de/grandma2/xml/MA, not ${parser.getAttributeValue("","xmlns")} \n POSITION: ${parser.positionDescription} \n\n\n")
        nextStartTag(parser) {}

        infoDate = parser.getAttributeValue("", "datetime")
        showfileName = parser.getAttributeValue("", "showfile")

        nextStartTag(parser) {}

        presetIndex = parser.getAttributeValue("", "index")?.toIntOrNull()?.plus(1)
        presetName = parser.getAttributeValue("", "name")

        nextStartTag(parser) {} // <InfoItems>
        nextStartTag(parser) {} // <Info >

        infoDate = parser.getAttributeValue("", "date")
        infoText = readText(parser)

        nextStartTag(parser) {} //Values
        nextStartTag(parser) {} //Channels

        deviceList = getChannels(parser)
        Log.v ("readPresetFiles()" , "finished Readout")


    }

    private fun getChannels (parser: XmlPullParser): List<Device?>{
        //start fun in <Channels> start tag
        val devices: MutableList<Device?> = mutableListOf()
        var condition = true
        var i = 0
        nextStartTag(parser) { condition = false }
        while (parser.name != "Preset" && condition  && parser.eventType != XmlPullParser.END_DOCUMENT && i < 300) {
            // Log.v("ReadoutMaExport: getChannels" , "Iteration | " + parser.positionDescription)
            i++
            when (parser.name) {
                "PresetValue" -> nextStartTag(parser) { condition = false }
                "Channel" -> {
                    devices.add(
                        Device(
                            null,
                            parser.getAttributeValue("", "fixture_id").toIntOrNull(),
                            parser.getAttributeValue("", "channel_id").toIntOrNull(),
                            null,
                            null,

                            )
                    )
                    nextStartTag(parser) {condition = false}
                }
                "Channels" -> return devices
                else -> return devices
            }
        }
        return devices
    }

    //returns true if the document has not ended, otherwise returns false
    private fun nextStartTag(parser:XmlPullParser, onDocEnd: () -> Unit) {
        //Log.v("ReadoutMaExport : nextStartTag", parser.positionDescription)
        var i = 0
        while (i < 50) {
            i++
            parser.next()
            if (parser.eventType == XmlPullParser.START_TAG) return
            if (parser.eventType == XmlPullParser.END_DOCUMENT) onDocEnd()
        }
    }


    private fun readText(parser: XmlPullParser): String {
        var result = ""
        while (true) {
            parser.next()
            if (parser.eventType == XmlPullParser.TEXT) {
                result = parser.text
            }
            if (parser.eventType == XmlPullParser.END_TAG) return result
        }

    }

}
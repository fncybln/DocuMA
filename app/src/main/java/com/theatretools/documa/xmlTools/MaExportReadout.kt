package com.theatretools.documa.xmlTools

import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.Xml
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.IOException
import java.io.InputStream

//val path = Environment.getExternalStorageDirectory().toString() + "/Pictures"

class  WrongShowfileException : Exception () {

}
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

class MaExportReadout {
    var presetName: String? = null
    var presetIndex: Int? = null
    var showfileName: String? = null
    var infoDate: String? = null
    var infoText: String? = null
    var deviceList: List<Device>? = null




    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream){
        inputStream.use {stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            readPresetFile(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPresetFile(parser: XmlPullParser,) {
        var devices: List<Device>? = listOfNotNull()
        var attrReadoutTime: String?
        //TODO: Include type if block
        //TODO: Include FixtureList
        parser.require(XmlPullParser.START_TAG, null,  "presets")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag.
            // Info Tag auf depth 2: attribute datetime und showfile name werden ausgelesen
            if (parser.name == "Info" && parser.depth == 2 && parser.eventType == XmlPullParser.START_TAG){

                //Showfile name wird abgeglichen
                //Attribut "showfile"
                if (showfileName != null){
                    if (showfileName != parser.getAttributeValue("", "showfile")) throw WrongShowfileException()
                    else Log.d("MaExportReadout", "showfile names dont match!!")
                    //TODO: Attribute name as String
                    //TODO: Error Handling!
                } else {
                    showfileName = parser.getAttributeValue("", "showfile")
                }

                //Datetime vom Showfile
                // Attribut "datetime"
                attrReadoutTime = parser.getAttributeValue("", "datetime")
            }

            if (parser.name == "Preset" && parser.depth == 2 && parser.eventType == XmlPullParser.START_TAG){
                presetIndex = parser.getAttributeValue("", "index").toIntOrNull()
                presetName= parser.getAttributeValue("", "name")

            }

            if (parser.name == "InfoItems" && parser.eventType == XmlPullParser.START_TAG) {
                parser.next()
                try {
                    readInfo(parser) {date, text -> infoDate = date; infoText = text}
                } catch (e: Exception){
                    Log.e(this::class.toString(), "$e\n -> InfoItems are Invalid")

                }
            }
            if (parser.name == "Values") {
                devices = readPresetEntry(parser)

            } else {
                skip(parser)
            }
        }
        deviceList = devices?.distinct()
    }
    private fun readInfo(parser: XmlPullParser, result: (date: String?, name: String?)-> Unit) {
        parser.require(XmlPullParser.START_TAG, null, "Info")
        result(parser.getAttributeValue("", "date"), readText(parser), )
    }

    private fun readPresetEntry(parser: XmlPullParser) : List<Device>{
        val deviceList = mutableListOf<Device>()
        parser.next()
        parser.require(XmlPullParser.START_TAG, null, "Channels")
        parser.next()
        var cond = true
        while (cond) {
            if (parser.name == "PresetValue") {
                parser.next()
            }
            if (parser.name == "Channel") {
                deviceList.add(Device (
                    null,
                    parser.getAttributeValue("", "channel_id").toIntOrNull(),
                    parser.getAttributeValue("", "fixture_id").toIntOrNull(),
                    null, null)
                )
            }
            if (parser.name == "Channels" && parser.eventType == XmlPullParser.END_TAG){
                cond = false
            }
        }
        return deviceList
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }

        }
    }
}
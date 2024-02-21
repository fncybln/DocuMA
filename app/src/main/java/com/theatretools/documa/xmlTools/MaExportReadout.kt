package com.theatretools.documa.xmlTools

import android.content.ContentResolver
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

class MaExportReadout : Readout() {

    var debug : String = ""
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
        
        try {
            var devices: List<Device>? = listOfNotNull()
            //TODO: Include type if block
            //TODO: Include FixtureList
            debug += ("\nparser pos:" + parser.positionDescription)
            var cond = true
            //parser.require(XmlPullParser.START_TAG, null,  "presets")
            while (cond) {
                parser.nextTag()
                Log.e("DEBUG", "<========================\n" +debug + "\n ========================>")
                debug += ("\nparser while loop: " + parser.positionDescription)
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }

                // Starts by looking for the entry tag.
                // Info Tag auf depth 2: attribute datetime und showfile name werden ausgelesen
                if (parser.name == "Info" && parser.depth == 2 && parser.eventType == XmlPullParser.START_TAG) {

                    //Showfile name wird abgeglichen
                    //Attribut "showfile"
                    if (showfileName != null) {
                        if (showfileName != parser.getAttributeValue(
                                "",
                                "showfile"
                            )
                        ) throw WrongShowfileException()

                        //TODO: Attribute name as String
                        //TODO: Error Handling!
                    } else {
                        showfileName = parser.getAttributeValue("", "showfile")
                    }

                    //Datetime vom Showfile
                    // Attribut "datetime"
                    readoutTime = parser.getAttributeValue("", "datetime")
                }

                if (parser.name == "Preset" && parser.eventType == XmlPullParser.START_TAG) {
                    presetIndex = parser.getAttributeValue("", "index").toIntOrNull()
                    presetName = parser.getAttributeValue("", "name")


                }
//                    && parser.eventType == XmlPullParser.START_TAG
                if (parser.name == "InfoItems") {
                    debug += ("\nparser: InfoItems -> next")
                    parser.next()
                    try {
                        readInfo(parser) { date, text -> infoDate = date; infoText = text }
                        debug += ("\ninfoData: $infoText | infoDate: $infoDate")
                    } catch (e: Exception) {
                        debug += ("\n$e\n -> InfoItems are Invalid")

                    }

                }
                if (parser.name == "Values") {
                    devices = readPresetEntry(parser)

                } else {
                    try {
                        parser.next()
//                        skip(parser)
                    } catch (e: IllegalStateException) {

                        debug += "\n paser.skip -> " + e.toString()
                    }
                }
                if (parser.name == "MA" && parser.eventType == XmlPullParser.END_TAG) cond = false
            }
            Log.e("DEBUG", debug)
            debug = ""
            deviceList = devices?.distinct()
            parser.next()
        }
        catch (e: Exception) {
            Log.e(this::class.toString(), "ERROR: ${e.toString()} \n $debug")
            parser.next()

        }
    }
    private fun readInfo(parser: XmlPullParser, result: (date: String?, name: String?)-> Unit) {
        parser.require(XmlPullParser.START_TAG, null, "Info")
        result(parser.getAttributeValue("", "date"), readText(parser), )
        debug += ("\nparser Position for readInfo:" + parser.positionDescription)
    }

    private fun readPresetEntry(parser: XmlPullParser) : List<Device>{
        val deviceList = mutableListOf<Device>()
        parser.next()
        try {
            parser.require(XmlPullParser.START_TAG, null, "Channels")

            parser.next()
            var cond = true
            while (cond) {

                if (parser.name == "PresetValue") {
                    debug += ( "parser: PresetValue -> next")
                    parser.next()
                }
                if (parser.name == "Channel") {

                    deviceList.add(Device (
                        null,
                        parser.getAttributeValue("", "channel_id").toIntOrNull(),
                        parser.getAttributeValue("", "fixture_id").toIntOrNull(),
                        null, null)
                    )
                    debug += ("\nparser: Channel -> DeviceAdd")
                }
                if (parser.name == "Channels" && parser.eventType == XmlPullParser.END_TAG){
                    cond = false
                }
                parser.nextTag()
            }
            debug += ( "- > De0viceList size: ${deviceList.size}")
        } catch (e: XmlPullParserException){
            debug += "\nreadPresetEntry: " + parser.positionDescription + " \n -> THROWS " + e.toString()
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
        debug += ("\nparser.skip: " + parser.positionDescription)
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
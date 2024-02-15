package com.theatretools.documa.xmlTools

import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.Xml
import com.theatretools.documa.dataobjects.PresetItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.IOException
import java.io.InputStream

//val path = Environment.getExternalStorageDirectory().toString() + "/Pictures"

class  WrongShowfileException : Exception () {

}


class MaExportReadout {
    var showfileName: String? = null
    var infoDate: String? = null
    var infoText: String? = null

    val entries = mutableListOf<PresetItem>()

    fun readout(uri: Uri) {
        val directory = File(uri.toString())
        val files = directory.listFiles()
        if (files != null) {
            Log.d("MaExportReadout", ("Size: " + files.size))
            for (i in files.indices) {
                Log.d("Files", "FileName:" + files[i].name)
            }
        }
        else Log.d ("MaExportReadout", ("Size: null") )

    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parse(inputStream: InputStream): List<PresetItem> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readPresetFile(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPresetFile(parser: XmlPullParser): List<PresetItem> {


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
                    if (showfileName != parser.getAttributeValue(1)) throw WrongShowfileException()
                    else Log.d("MaExportReadout", "showfile names dont match!!")
                    //TODO: Attribute name as String
                    //TODO: Error Handling!
                } else {
                    showfileName = parser.getAttributeValue(1)
                }

                //Datetime vom Showfile
                // Attribut "datetime"
                attrReadoutTime = parser.getAttributeValue(0)
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
                entries.add(readPresetEntry(parser, ))
            } else {
                skip(parser)
            }
        }
        return entries
    }
    private fun readInfo(parser: XmlPullParser, result: (date: String?, name: String?)-> Unit) {
        parser.require(XmlPullParser.START_TAG, null, "Info")
        result(parser.getAttributeValue(0), readText(parser), )
    }

    private fun readPresetEntry(parser: XmlPullParser, addItem:(item: PresetItem) -> Unit) :  {
        parser.next()
        parser.require(XmlPullParser.START_TAG, null, "Channels")
        parser.next()
        var cond = true
        while (cond) {
            if
        }
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
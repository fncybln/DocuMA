package com.theatretools.documa.xmlTools


import android.util.Xml
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class PresetReadout (

) {

    // We don't use namespaces.
    private val ns: String? = null


    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<PresetItem> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readPresetFile(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun readPresetFile(parser: XmlPullParser): List<PresetItem> {
        val entries = mutableListOf<PresetItem>()
        //TODO: Include type if block
        //TODO: Include FixtureList
        parser.require(XmlPullParser.START_TAG, ns, "presets")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag.

            if (parser.name == "preset") {
                entries.add(readEntry_preset(parser))
            } else {
                skip(parser)
            }
        }
        return entries
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry_preset(parser: XmlPullParser): PresetItem {
        parser.require(XmlPullParser.START_TAG, ns, "preset")
        var id: Int? = null
        var preset_id: Int? = null
        var preset_name: String? = null
        var preset_info: String? = null
        var all_picture_name: String? = null
        var fix_chan: List<DeviceInPreset>? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "id" -> id = readId(parser)
                "preset_id" -> preset_id = readPresetId(parser)
                "preset_name" -> preset_name = readPresetName(parser)
                "preset_info" -> preset_info= readInfo(parser)
                "all_picture_name" -> all_picture_name = readAllPic(parser)
                "fix_chan" -> fix_chan = readFixChan(parser)
                else -> skip(parser)
            }
        }
        return PresetItem(id, preset_id, preset_name, preset_info, all_picture_name)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readId(parser: XmlPullParser): Int? {
        parser.require(XmlPullParser.START_TAG, ns, "id")
        val id = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "id")
        return try {
            id.toInt()
        } catch (e: NumberFormatException){
            throw NumberFormatException("Field ID is not an Int!")
        }
    }
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readPresetId(parser: XmlPullParser): Int? {
        parser.require(XmlPullParser.START_TAG, ns, "preset_id")
        val id = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "preset_id")
        return try {
            id.toInt()
        } catch (e: NumberFormatException){
            null
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readPresetName(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "preset_name")
        val text = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "preset_name")
        return text
    }

    private fun readInfo(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "preset_info")
        val text = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "preset_info")
        return text
    }

    private fun readAllPic(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "all_picture_name")
        val text = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "all_picture_name")
        return text
    }
    @Throws(NumberFormatException::class)
    private fun readFixChan(parser: XmlPullParser): MutableList<DeviceInPreset>? {

        if (parser.eventType != XmlPullParser.START_TAG){
            throw IllegalStateException()
        }
        val list: MutableList<DeviceInPreset>? = null
        var fcId: Int? = null
        var fix: Int? = null
        var chan: Int? = null
        var ind_picture_name: String? = null

        do {
            try{
                when (parser.name){
                    "fc_id" -> fcId = readId(parser)
                    "fix" -> fix = readText(parser).toInt()
                    "chan" -> chan = readText(parser).toInt()
                    "ind_picture_name" -> ind_picture_name = readText(parser)
                }
            }
            catch (e: NumberFormatException ){
                throw NumberFormatException("<fc_id>, <fix> or <chan> tag doesn't contain an Int")
            }

            list?.add(DeviceInPreset(fcId, null, fix, chan, ind_picture_name))

            parser.next()


        }while(parser.name != "fix_chan" && parser.eventType != XmlPullParser.END_TAG)
        return list
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


// Processes title tags in the feed.



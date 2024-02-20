package com.theatretools.documa

import android.util.Log
import android.util.Xml
import com.theatretools.documa.dataobjects.Device
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class ReadoutMaExport  {

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
        var cond = true

        if (parser.getAttributeValue("", "xlms") == "http://schemas.malighting.de/grandma2/xml/MA") {
            Log.v(this::class.toString(), "xlms scheme = http://schemas.malighting.de/grandma2/xml/MA")
        } else throw Exception ("xlms scheme should be xlms scheme = http://schemas.malighting.de/grandma2/xml/MA, not ${parser.getAttributeValue("","xlms")}")

        // Document Header

        while (cond) {

        }



    }

}
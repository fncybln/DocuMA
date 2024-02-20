package com.theatretools.documa

import org.junit.Test

import org.junit.Assert.*
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val targetStream: InputStream = ByteArrayInputStream(data.toByteArray())
        ReadoutMaExport().parse(targetStream)
    }

    var data = """
        <?xml version="1.0" encoding="utf-8"?>
        <MA xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://schemas.malighting.de/grandma2/xml/MA" xsi:schemaLocation="http://schemas.malighting.de/grandma2/xml/MA http://schemas.malighting.de/grandma2/xml/3.9.60/MA.xsd" major_vers="3" minor_vers="9" stream_vers="60">
        	<Info datetime="2024-01-13T11:30:03" showfile="b_bucket_2024_01_13" />
        	<Preset index="0" name="FOH Flaeche" SpecialUse="Normal">
        		<InfoItems>
        			<Info date="2023-12-02T13:23:02">Arbeitslicht</Info>
        		</InfoItems>
        		<Values>
        			<Channels>
        				<PresetValue Value="12.964128">
        					<Channel fixture_id="1" channel_id="1" attribute_name="PAN" />
        				</PresetValue>
        				<PresetValue Value="46.847065">
        					<Channel fixture_id="1" channel_id="1" attribute_name="TILT" />
        				</PresetValue>
        				<PresetValue Value="50.196079">
        					<Channel fixture_id="1" channel_id="1" attribute_name="FOCUS" />
        				</PresetValue>
        				<PresetValue Value="69.696075">
        					<Channel fixture_id="1" channel_id="1" attribute_name="ZOOM" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="1" channel_id="1" attribute_name="FROST" />
        				</PresetValue>
        				<PresetValue Value="25.826963">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE3A" />
        				</PresetValue>
        				<PresetValue Value="25.826963">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE3B" />
        				</PresetValue>
        				<PresetValue Value="8.0008669">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE4A" />
        				</PresetValue>
        				<PresetValue Value="52.000828">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE4B" />
        				</PresetValue>
        				<PresetValue Value="28.228954">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE1A" />
        				</PresetValue>
        				<PresetValue Value="28.228954">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE1B" />
        				</PresetValue>
        				<PresetValue Value="0.00085999462">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE2A" />
        				</PresetValue>
        				<PresetValue Value="0.00085999462">
        					<Channel fixture_id="1" channel_id="1" attribute_name="BLADE2B" />
        				</PresetValue>
        				<PresetValue Value="38.888935">
        					<Channel fixture_id="1" channel_id="1" attribute_name="SHAPER ROT" />
        				</PresetValue>
        				<PresetValue Value="9.3641224">
        					<Channel fixture_id="2" channel_id="2" attribute_name="PAN" />
        				</PresetValue>
        				<PresetValue Value="49.547028">
        					<Channel fixture_id="2" channel_id="2" attribute_name="TILT" />
        				</PresetValue>
        				<PresetValue Value="50.196079">
        					<Channel fixture_id="2" channel_id="2" attribute_name="FOCUS" />
        				</PresetValue>
        				<PresetValue Value="69.696075">
        					<Channel fixture_id="2" channel_id="2" attribute_name="ZOOM" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="2" channel_id="2" attribute_name="FROST" />
        				</PresetValue>
        				<PresetValue Value="25.826683">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE3A" />
        				</PresetValue>
        				<PresetValue Value="25.826683">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE3B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE4A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE4B" />
        				</PresetValue>
        				<PresetValue Value="29.200378">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE1A" />
        				</PresetValue>
        				<PresetValue Value="29.200378">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE1B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE2A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="2" channel_id="2" attribute_name="BLADE2B" />
        				</PresetValue>
        				<PresetValue Value="41.111134">
        					<Channel fixture_id="2" channel_id="2" attribute_name="SHAPER ROT" />
        				</PresetValue>
        				<PresetValue Value="1.8041199">
        					<Channel fixture_id="3" channel_id="3" attribute_name="PAN" />
        				</PresetValue>
        				<PresetValue Value="43.742077">
        					<Channel fixture_id="3" channel_id="3" attribute_name="TILT" />
        				</PresetValue>
        				<PresetValue Value="50.196079">
        					<Channel fixture_id="3" channel_id="3" attribute_name="FOCUS" />
        				</PresetValue>
        				<PresetValue Value="69.696075">
        					<Channel fixture_id="3" channel_id="3" attribute_name="ZOOM" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="3" channel_id="3" attribute_name="FROST" />
        				</PresetValue>
        				<PresetValue Value="25.826683">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE3A" />
        				</PresetValue>
        				<PresetValue Value="25.826683">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE3B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE4A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE4B" />
        				</PresetValue>
        				<PresetValue Value="29.200378">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE1A" />
        				</PresetValue>
        				<PresetValue Value="29.200378">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE1B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE2A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="3" channel_id="3" attribute_name="BLADE2B" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="3" channel_id="3" attribute_name="SHAPER ROT" />
        				</PresetValue>
        				<PresetValue Value="-11.515886">
        					<Channel fixture_id="4" channel_id="4" attribute_name="PAN" />
        				</PresetValue>
        				<PresetValue Value="49.14204">
        					<Channel fixture_id="4" channel_id="4" attribute_name="TILT" />
        				</PresetValue>
        				<PresetValue Value="50.196079">
        					<Channel fixture_id="4" channel_id="4" attribute_name="FOCUS" />
        				</PresetValue>
        				<PresetValue Value="69.696075">
        					<Channel fixture_id="4" channel_id="4" attribute_name="ZOOM" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="4" channel_id="4" attribute_name="FROST" />
        				</PresetValue>
        				<PresetValue Value="24.726694">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE3A" />
        				</PresetValue>
        				<PresetValue Value="24.726694">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE3B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE4A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE4B" />
        				</PresetValue>
        				<PresetValue Value="27.850403">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE1A" />
        				</PresetValue>
        				<PresetValue Value="27.850403">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE1B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE2A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="4" channel_id="4" attribute_name="BLADE2B" />
        				</PresetValue>
        				<PresetValue Value="65.555656">
        					<Channel fixture_id="4" channel_id="4" attribute_name="SHAPER ROT" />
        				</PresetValue>
        				<PresetValue Value="-11.605886">
        					<Channel fixture_id="5" channel_id="5" attribute_name="PAN" />
        				</PresetValue>
        				<PresetValue Value="48.602039">
        					<Channel fixture_id="5" channel_id="5" attribute_name="TILT" />
        				</PresetValue>
        				<PresetValue Value="50.196079">
        					<Channel fixture_id="5" channel_id="5" attribute_name="FOCUS" />
        				</PresetValue>
        				<PresetValue Value="69.696075">
        					<Channel fixture_id="5" channel_id="5" attribute_name="ZOOM" />
        				</PresetValue>
        				<PresetValue Value="50">
        					<Channel fixture_id="5" channel_id="5" attribute_name="FROST" />
        				</PresetValue>
        				<PresetValue Value="0">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE3A" />
        				</PresetValue>
        				<PresetValue Value="46.226692">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE3B" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE4A" />
        				</PresetValue>
        				<PresetValue Value="0.00038999875">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE4B" />
        				</PresetValue>
        				<PresetValue Value="27.250393">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE1A" />
        				</PresetValue>
        				<PresetValue Value="27.250393">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE1B" />
        				</PresetValue>
        				<PresetValue Value="56.150368">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE2A" />
        				</PresetValue>
        				<PresetValue Value="9.3499784">
        					<Channel fixture_id="5" channel_id="5" attribute_name="BLADE2B" />
        				</PresetValue>
        				<PresetValue Value="77.778046">
        					<Channel fixture_id="5" channel_id="5" attribute_name="SHAPER ROT" />
        				</PresetValue>
        			</Channels>
        		</Values>
        	</Preset>
        </MA>
    """.trimIndent()
}
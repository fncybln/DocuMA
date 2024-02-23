package com.theatretools.documa

import com.theatretools.documa.dataobjects.Device
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
        var devices = mutableListOf<Device>(
            Device(null, 1, 1, null, null),
            Device(null, 1, 1, null, null),
            Device(null, 1, 1, null, null),
            Device(null, 2, 2, null, null),
        )
        val newVar = devices.distinctBy { it.fix!! }
        newVar.forEach{println(it.toString() + "\n")}
    }
}


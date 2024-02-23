package com.theatretools.documa.telnet

import android.util.Log
import java.io.BufferedInputStream
import java.io.IOException
import java.io.OutputStream
import java.net.SocketException
import kotlin.jvm.Throws

class TelnetConnection (var SERVER_IP: String, var SERVER_PORT: Int){
    var rawClient = org.apache.commons.net.telnet.TelnetClient()

    @Throws(IOException::class)
    fun connect () {
        try{
            rawClient.connect(SERVER_IP, SERVER_PORT)
        } catch (e: SocketException){
            throw SocketException("Connection Error :(")
        }
    }

    var getReader: BufferedInputStream = BufferedInputStream(rawClient.getInputStream())
    var getOutput: OutputStream = rawClient.getOutputStream()
    var isConnected: Boolean? = rawClient.isConnected()
    var getClient = rawClient



    fun disconnect() : Boolean {
        return try {
            rawClient.disconnect()
            true
        } catch (e: IOException) {
            Log.e("TelnetConnection", "Error disconnecting: $e \n${e.stackTrace}" )
            false
        }
    }
}

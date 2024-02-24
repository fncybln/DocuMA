package com.theatretools.documa.telnet

import android.util.Log
import java.io.BufferedInputStream
import java.io.IOException
import java.io.OutputStream
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

// This is just a worker class.
// Requests etc should be made via the TelnetClient Class.

class TelnetConnection  (var SERVER_IP: String,
var SERVER_PORT: Int) {

    var rawClient = org.apache.commons.net.telnet.TelnetClient()

    var getReader: BufferedInputStream = BufferedInputStream(rawClient.getInputStream())
    var getOutput: OutputStream? = rawClient.outputStream
    var isConnected: Boolean? = rawClient.isConnected
    var getClient = rawClient

    @Throws(IOException::class)
    fun connect () {
        if (SERVER_IP == "null") {return} // Instance can be created without
//        try{
            rawClient.connect(SERVER_IP, SERVER_PORT)
//        } catch (e: SocketException){
//            Log.e("TelnetConnection", "Connection Error: ${e.stackTrace}\n$e")
//            throw e
//        }
    }
    fun connect(ip : String, port: Int?){
        SERVER_PORT = port?: 30000
        SERVER_IP = ip
        try{
            Log.v("TelnetConnection", "Attempting connection...")
            rawClient.connectTimeout = 30000
            rawClient.connect(SERVER_IP, SERVER_PORT)
            Log.v("TelnetConnection", "Connection Status: ${rawClient.isConnected}")
            if (rawClient.isConnected) isConnected = true
        } catch (e: SocketException){
            Log.e("TelnetConnection", "Connection Error: ${e.stackTrace}\n $e")
            throw e
        } catch (e: Exception) {
            Log.e("TelnetConnection", "Exception: ${e.stackTrace}\n $e")
            throw e
        } catch (e: UnknownHostException) {
            Log.e("TelnetConnection", "UnknownHostException: ${e.stackTrace}\n $e")
            throw e
        }
    }





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

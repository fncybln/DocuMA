package com.theatretools.documa.telnet


import android.util.Log
import org.apache.commons.io.input.TeeInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.Locale


open class TelnetClient (ip: String, port: Int){

    companion object {
        val STATUS_CONNECTED = 1
        val STATUS_DISCONNECTED = 0
    }

    private var outputStream: OutputStream? = null
    private var rawConnection: org.apache.commons.net.telnet.TelnetClient? = null
    private var inputStream: InputStream? = null
    private var spyReader : PipedInputStream? = null
    private var telnetConnection = com.theatretools.documa.telnet.TelnetConnection(ip, port)
    private var buf : BufferedReader? = null

    var status : Int = STATUS_DISCONNECTED
    init {
        rawConnection = telnetConnection.getClient
        outputStream = telnetConnection.getOutput
        inputStream = telnetConnection.getReader
    }

    fun isConnected():Boolean?{
        return telnetConnection.isConnected
    }


    fun connect(ip: String, port: Int?, onResult: (Boolean, Exception?) -> Unit) {

        try {
            telnetConnection.connect(ip, port)
            Log.v("TelnetClient.connect()", "connecting to telnet Client...")
            Log.v("TelnetClient.connect()", "Connection Status: ${telnetConnection.isConnected}.")
            status = STATUS_CONNECTED
            onResult(true, null)
        } catch (e: Exception) {
            onResult(false, e)
        }

    }


    fun close (): Boolean{
        buf?.close()
        status = STATUS_DISCONNECTED
        Log.i("TelnetClient" , "Telnet Client disconnected.")
        return telnetConnection.disconnect()

    }

    fun sendCommand (cmd: String): Boolean {
        if (telnetConnection.isConnected == false) {
            return false
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append(cmd.uppercase(Locale.ENGLISH))
        stringBuilder.append("\n\r")

        val cmdByte = stringBuilder.toString().toByteArray()

        return try {
            outputStream!!.write(cmdByte, 0 , cmdByte.size)
            outputStream!!.flush()
            true
        } catch (e: Exception) {
            Log.e("TelnetClient", "Exception when writing the outputStream: \n${e.stackTrace}")
            false
        }
    }

    fun getResponse(cmd: String, onResult: (String) -> Unit) : String {
        if (telnetConnection.isConnected == false || status == STATUS_DISCONNECTED) {
            Log.v ("TelnetClient.getResponse()", "Client disconnected, cant send message")
            throw IOException("Client disconnected, cant send message")
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append(cmd.uppercase(Locale.ENGLISH))
        stringBuilder.append("\n\r")

        val cmdByte = stringBuilder.toString().toByteArray()
        buf = BufferedReader(InputStreamReader(rawConnection?.inputStream))
        var result: String? = ""
        var done = false

//        val a: InputStreamReader? = spawnSpy()
//        val buf = TestBufferedReader(a)



        try {

            var response = StringBuilder()
            outputStream?.write(cmdByte, 0, cmdByte.size)
            outputStream?.flush()
            Log.v("TelnetClient", "reading out buffer...")
            while (buf!!.ready()) {
                response.append( buf!!.read().toChar() )
            }
            buf!!.close()
            Log.v("TelnetClient", "buffer closed")
            result = response.toString()

            Log.v("TelnetClient", "buffer content: $result")
        } catch (e: Exception) {
            Log.e("TelnetClient", "Exception when writing the outputStream: \n${e.printStackTrace()}")
        }

//        Log.v("TelnetClient.getResponse()", "Collected Buffer: ${buf.readLine()}")


        Log.v ("TelnetClient.getResponse()", "Sent: $cmd \n$cmdByte")
        Log.v ("TelnetClient.getResponse()", "Recieved: $result")
        onResult(result!!)
        return result

    }

    @Throws(InterruptedException::class, IOException::class)
    fun spawnSpy(): InputStreamReader? {
        val `in` = PipedInputStream()
        val out = PipedOutputStream()
        `in`.connect(out)
        return if (spyReader != null) {
            spawnSpy(spyReader!!, out)
        } else {
            spyReader = `in`
            spawnSpy(inputStream!!, out)
        }
    }
    @Throws(InterruptedException::class)
    private fun spawnSpy(`in`: InputStream, pipeout: PipedOutputStream): InputStreamReader {
        return InputStreamReader(TeeInputStream(`in`, pipeout))
    }


}
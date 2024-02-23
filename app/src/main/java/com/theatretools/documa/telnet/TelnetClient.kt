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
import java.util.LinkedList
import java.util.Locale


class TelnetClient (ip: String, port: Int){
    private var client: TelnetConnection? = null
    private var outputStream: OutputStream? = null
    private var rawConnection: org.apache.commons.net.telnet.TelnetClient? = null
    private var inputStream: InputStream? = null
    private var threads = LinkedList<Thread>()
    private var spyReader : PipedInputStream? = null
    private var telnetConnection = TelnetConnection(ip, port)

    init {
        telnetConnection.connect()
        rawConnection = telnetConnection.getClient
        outputStream = telnetConnection.getOutput
        inputStream = telnetConnection.getReader
    }


    fun close (): Boolean{
        return telnetConnection.disconnect()
    }

    fun sendCommand (cmd: String): Boolean {
        if (client == null || client?.isConnected == false) {
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

    fun getResponse(cmd: String) : String {
        if (client == null || client?.isConnected == false) {
            throw IOException("Client disconnected, cant send message")
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append(cmd.uppercase(Locale.ENGLISH))
        stringBuilder.append("\n\r")

        val cmdByte = stringBuilder.toString().toByteArray()


        val a: InputStreamReader? = spawnSpy()
        val buf = BufferedReader(a)

        while (buf.ready()) buf.read()
        outputStream?.write(cmdByte, 0, cmdByte.size)
        outputStream?.flush()

        var result: String?
        var done = false

        do {
            result = buf.readLine()
            if (result != null) done = true
        } while (!done)

        return result!!


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
package org.locallback.natives.pipe

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.io.IOException
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Connexus(private val host: String, private val port: Int) : Closeable {

    val log: Logger = LogManager.getLogger("SocketClient")

    private lateinit var socket: Socket
    private lateinit var writer: BufferedWriter
    private lateinit var reader: BufferedReader

    init {
        runBlocking { connect() }
    }

    private suspend fun connect(retries: Int = 5, delayMillis: Long = 2000) = withContext(Dispatchers.IO) {
        repeat(retries) { attempt ->
            try {
                socket = Socket(host, port)
                writer = socket.getOutputStream().bufferedWriter()
                reader = socket.getInputStream().bufferedReader()
                log.info("Connected to server $host:$port")
                return@withContext
            } catch (e: IOException) {
                log.error("Failed to connect to server (attempt ${attempt + 1}): ${e.message}")
                if (attempt < retries - 1) {
                    delay(delayMillis)
                } else {
                    throw e
                }
            }
        }
    }

    suspend fun invoke(functionName: String, args: Array<out String>): String? = withContext(Dispatchers.IO) {
        if (socket.isClosed || !socket.isConnected) {
            connect()
        }

        val message = buildString {
            append("{\"function\": \"$functionName\", \"args\": [")
            append(args.joinToString(",") { "\"$it\"" })
            append("]}")
        }

        writer.apply {
            write(message)
            newLine()
            flush()
        }

        return@withContext reader.readLine()
    }

    override fun close() {
        writer.close()
        reader.close()
        socket.close()
        log.info("Disconnected from server.")
    }
}
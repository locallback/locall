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
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Connexus(private val host: String, private val port: Int) : Closeable {

    private val log: Logger = LogManager.getLogger("Connexus")
    private var socket: Socket? = null
    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    init {
        runBlocking { connect() }
    }

    private suspend fun connect(retries: Int = 5, delayMillis: Duration = 2.seconds) = withContext(Dispatchers.IO) {
        repeat(retries) { attempt ->
            try {
                socket = Socket(host, port).also {
                    writer = it.getOutputStream().bufferedWriter()
                    reader = it.getInputStream().bufferedReader()
                }
                log.info("Connected to $host:$port")
                return@withContext
            } catch (e: IOException) {
                log.error("Connection attempt ${attempt + 1} failed: ${e.message}")
                if (attempt < retries - 1) delay(delayMillis)
                else throw e
            }
        }
    }

    suspend fun send(functionName: String, args: Array<out String>): String? = withContext(Dispatchers.IO) {
        if (socket?.isClosed != false || socket?.isConnected != true) connect()

        val message = buildJsonMessage(functionName, args)
        writer?.apply {
            write(message)
            newLine()
            flush()
        }
        return@withContext reader?.readLine()
    }

    // There is a risk. Write it this way for now.
    private fun buildJsonMessage(functionName: String, args: Array<out String>) = buildString {
        append("{\"function\": \"$functionName\", \"args\": [")
        append(args.joinToString(",") { "\"$it\"" })
        append("]}")
    }

    override fun close() {
        writer?.close()
        reader?.close()
        socket?.close()
        log.info("Disconnected from server.")
    }

}
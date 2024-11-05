package org.locallback.natives.pipe

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.net.Socket

class SocketClient(private val host: String, private val port: Int) : AutoCloseable {
    private lateinit var socket: Socket
    private lateinit var writer: BufferedWriter
    private lateinit var reader: BufferedReader

    init {
        connect()
    }

    private fun connect() {
        while (true) {
            try {
                socket = Socket(host, port)
                writer = socket.getOutputStream().bufferedWriter()
                reader = socket.getInputStream().bufferedReader()
                println("Connected to server $host:$port")
                break
            } catch (_: IOException) {
                println("Failed to connect to server $host:$port, retrying...")
                Thread.sleep(1000)
            }
        }
    }

    fun invoke(functionName: String, args: Array<String>): String? {
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

        return reader.readLine()
    }

    override fun close() {
        try {
            writer.close()
            reader.close()
            socket.close()
            println("Disconnected from server.")
        } catch (e: IOException) {
            println("Error while closing resources: ${e.message}")
        }
    }
}

fun main() {
    SocketClient("127.0.0.1", 8081).use { client ->
        val response1 = client.invoke("add", arrayOf("10", "20"))
        println("FunctionName: add, Response: $response1")

        val response2 = client.invoke("subtract", arrayOf("30", "10"))
        println("FunctionName: subtract, Response: $response2")
    }
}
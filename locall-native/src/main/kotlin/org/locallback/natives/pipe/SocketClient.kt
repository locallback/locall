package org.locallback.natives.pipe

import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket

class SocketClient(private val host: String, private val port: Int) : AutoCloseable {
    private val socket: Socket by lazy { Socket(host, port) }
    private val writer: BufferedWriter by lazy { socket.getOutputStream().bufferedWriter() }
    private val reader: BufferedReader by lazy { socket.getInputStream().bufferedReader() }

    fun connect() {
        println("Connected to server $host:$port")
    }

    fun invoke(functionName: String, args: Array<String>): String? {
        check(!socket.isClosed) { "Socket is not connected. Please call connect() first." }

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
        writer.close()
        reader.close()
        socket.close()
        println("Disconnect server.")
    }
}

fun main() {
    SocketClient("127.0.0.1", 8081).use { client ->
        client.connect()

        val response = client.invoke("add", arrayOf("10", "20"))
        println("FunctionName: add, Response: $response")
    }
}
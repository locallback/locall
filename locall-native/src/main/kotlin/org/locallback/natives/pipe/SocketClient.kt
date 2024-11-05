package org.locallback.natives.pipe

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class SocketClient(host: String, private val port: Int) : AutoCloseable {

    private val socket: DatagramSocket = DatagramSocket()
    private val serverAddress: InetAddress = InetAddress.getByName(host)

    fun invoke(functionName: String, args: Array<out String>): String? {
        val message = buildString {
            append("{\"function\": \"$functionName\", \"args\": [")
            append(args.joinToString(",") { "\"$it\"" })
            append("]}")
        }

        val sendData = message.toByteArray()
        val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, port)
        socket.send(sendPacket)

        val receiveData = ByteArray(2048)
        val receivePacket = DatagramPacket(receiveData, receiveData.size)
        return try {
            socket.receive(receivePacket)
            val response = String(receivePacket.data, 0, receivePacket.length)
            response
        } catch (e: IOException) {
            println("Error receiving response: ${e.message}")
            null
        }
    }

    override fun close() {
        socket.close()
        println("Socket closed.")
    }
}
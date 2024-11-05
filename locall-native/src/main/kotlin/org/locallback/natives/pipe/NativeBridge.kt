package org.locallback.natives.pipe

class NativeBridge {

    companion object {
        private val socketClient: SocketClient by lazy {
            SocketClient("127.0.0.1", 8081).also {
                println("Socket client created")
            }
        }

        @JvmStatic
        suspend fun invoke(functionName: String, vararg args: String): String? {
            return try {
                socketClient.invoke(functionName, args)
            } catch (e: Exception) {
                println("Error invoking function: ${e.message}")
                null
            }
        }
    }

}
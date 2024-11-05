package org.locallback.natives.pipe

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class NativeBridge {

    val log: Logger = LogManager.getLogger("SocketClient")

    companion object {
        private val socketClient: Connexus by lazy {
            Connexus("127.0.0.1", 8081).also {
                log.info("Socket client created")
            }
        }

        @JvmStatic
        suspend fun invoke(functionName: String, vararg args: String): String? {
            return try {
                socketClient.invoke(functionName, args)
            } catch (e: Exception) {
                log.error("Error invoking function: ${e.message}")
                null
            }
        }
    }

}
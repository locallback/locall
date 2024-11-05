package org.locallback.natives.pipe

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class NativeCallBridge {

    companion object {
        private val log: Logger = LogManager.getLogger("NativeCallBridge")

        @Volatile
        private var socketClient: Connexus? = null

        @JvmStatic
        fun init() {
            if (socketClient == null) {
                synchronized(this) {
                    if (socketClient == null) {
                        socketClient = Connexus("127.0.0.1", 8081).also {
                            log.info("Socket client created")
                        }
                    }
                }
            }
        }

        @JvmStatic
        suspend fun invoke(functionName: String, vararg args: String): String? {
            return try {
                if (socketClient == null) {
                    log.error("Socket client is not initialized. Call NativeBridge.init() first.")
                    return null
                }
                socketClient?.invoke(functionName, args)
            } catch (e: Exception) {
                log.error("Error invoking function: ${e.message}")
                null
            }
        }
    }
}
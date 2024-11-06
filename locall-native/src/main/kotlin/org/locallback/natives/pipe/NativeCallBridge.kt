package org.locallback.natives.pipe

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class NativeCallBridge(private val ip: String, private val port: Int) {

    private val log: Logger = LogManager.getLogger("NativeCallBridge")
    private var socketClient: Connexus? = null

    init {
        initConnexus()
    }

    private fun initConnexus() {
        if (socketClient == null) {
            synchronized(this) {
                if (socketClient == null) {
                    socketClient = Connexus(ip, port).also {
                        log.info("Socket client created with IP: $ip and Port: $port")
                    }
                }
            }
        }
    }

    suspend fun invoke(functionName: String, vararg args: String): String? {
        return try {
            if (socketClient == null) {
                log.error("Socket client is not initialized.")
                return null
            }
            socketClient?.invoke(functionName, args)
        } catch (e: Exception) {
            log.error("Error invoking function: ${e.message}")
            null
        }
    }
}
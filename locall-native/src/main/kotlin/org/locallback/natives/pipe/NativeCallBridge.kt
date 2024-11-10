package org.locallback.natives.pipe

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object NativeCallBridgeFactory {

    private val instances = mutableMapOf<Pair<String, Int>, NativeCallBridge>()

    @JvmStatic
    fun create(ip: String, port: Int): NativeCallBridge {
        return instances.getOrPut(ip to port) {
            NativeCallBridge.newInstance(ip, port)
        }
    }

}

class NativeCallBridge private constructor(private val ip: String, private val port: Int) {

    private val log: Logger = LogManager.getLogger("NativeCallBridge")
    private val connexus: Connexus = Connexus(ip, port).also {
        log.info("NativeCallBridge created, IP: $ip Port: $port")
    }

    companion object {
        fun newInstance(ip: String, port: Int): NativeCallBridge = NativeCallBridge(ip, port)
    }

    fun call(functionName: String, args: Array<out String>): String? {
        return try {
            // TODO: 待优化
            runBlocking {
                connexus.send(functionName, args)
            }
        } catch (e: Exception) {
            log.error("Error calling function: ${e.message}")
            null
        }
    }

}

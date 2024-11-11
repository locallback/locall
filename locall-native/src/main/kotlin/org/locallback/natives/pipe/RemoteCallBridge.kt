package org.locallback.natives.pipe

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object CallBridgeFactory {

    private val instances = mutableMapOf<Pair<String, Int>, RemoteCallBridge>()

    @JvmStatic
    fun create(ip: String, port: Int): RemoteCallBridge {
        return instances.getOrPut(ip to port) {
            RemoteCallBridge.newInstance(ip, port)
        }
    }

}

class RemoteCallBridge private constructor(private val ip: String, private val port: Int) {

    private val log: Logger = LogManager.getLogger("[RemoteCallBridge]")
    private val connexus: Connexus = Connexus(ip, port).also {
        log.info("RemoteCallBridge created, IP: $ip Port: $port")
    }

    companion object {
        fun newInstance(ip: String, port: Int): RemoteCallBridge = RemoteCallBridge(ip, port)
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

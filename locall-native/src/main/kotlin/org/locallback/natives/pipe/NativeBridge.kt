package org.locallback.natives.pipe

class NativeBridge {

    companion object {
        private val socketClient: SocketClient = SocketClient("127.0.0.1", 8081)

        @JvmStatic
        fun invoke(functionName: String, args: Array<String>): String? {
            return socketClient.invoke(functionName, args)
        }
    }

}
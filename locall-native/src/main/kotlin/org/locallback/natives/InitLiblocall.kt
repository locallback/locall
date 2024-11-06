package org.locallback.natives

import kotlinx.coroutines.runBlocking
import org.locallback.natives.pipe.NativeCallBridge
import kotlin.random.Random

class InitLiblocall {

    init {

//        System.loadLibrary("liblocall")
    }

    external fun initLiblocall()
}

fun main() = runBlocking {
//    InitLiblocall()
    val bridge1 = NativeCallBridge("127.0.0.1", 8081)
    val results = mutableListOf<String>()
    val times = mutableListOf<Long>()

    repeat(10) {
        val arg1 = Random.nextInt(1, 100).toString()
        val arg2 = Random.nextInt(1, 100).toString()

        val (response, deltaTime) = measureTime {
            runBlocking {
                bridge1.invoke("add", arg1, arg2)
            }
        }

        results.add(response ?: "null")
        times.add(deltaTime)
    }

    println("------------------------------------------------")
    println(String.format("%-12s | %s |", "response:", results.joinToString(" | ")))
    println(String.format("%-12s | %s |", "delta_time:", times.joinToString(" | ")))
    println("------------------------------------------------")

    results.clear()
    times.clear()

    val bridge2 = NativeCallBridge("192.168.1.100", 8082)

    repeat(10) {
        val arg1 = Random.nextInt(1, 100).toString()
        val arg2 = Random.nextInt(1, 100).toString()

        val (response, deltaTime) = measureTime {
            runBlocking {
                bridge2.invoke("add", arg1, arg2)
            }
        }

        results.add(response ?: "null")
        times.add(deltaTime)
    }

    println("------------------------------------------------")
    println(String.format("%-12s | %s |", "response:", results.joinToString(" | ")))
    println(String.format("%-12s | %s |", "delta_time:", times.joinToString(" | ")))
    println("------------------------------------------------")
}

inline fun <T> measureTime(block: () -> T): Pair<T?, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    val deltaTime = end - start
    return Pair(result, deltaTime)
}
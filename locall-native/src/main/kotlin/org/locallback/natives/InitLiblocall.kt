package org.locallback.natives

import kotlinx.coroutines.runBlocking
import org.locallback.natives.pipe.NativeBridge
import kotlin.random.Random

class InitLiblocall {

    init {
        System.loadLibrary("liblocall")
    }

    external fun initLiblocall()
}

fun main() = runBlocking {
    val results = mutableListOf<String>()
    val times = mutableListOf<Long>()

    repeat(10) {
        val arg1 = Random.nextInt(1, 100).toString()
        val arg2 = Random.nextInt(1, 100).toString()

        val (response, deltaTime) = measureTime {
            runBlocking {
                NativeBridge.invoke("add", arg1, arg2)
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
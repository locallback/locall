package org.locallback.natives

import org.locallback.natives.pipe.NativeBridge
import kotlin.random.Random

class InitLiblocall {

    init {
        System.loadLibrary("liblocall")
    }

    external fun initLiblocall()
}

fun main() {
    val results = mutableListOf<String>()
    val times = mutableListOf<Long>()

    repeat(10) {
        val arg1 = Random.nextInt(1, 100).toString()
        val arg2 = Random.nextInt(1, 100).toString()
        val start = System.currentTimeMillis()
        val response = NativeBridge.invoke("add", arrayOf(arg1, arg2))
        val end = System.currentTimeMillis()
        val deltaTime = end - start

        results.add(response ?: "null")
        times.add(deltaTime)
    }

    println("------------------------------------------------")
    println(String.format("%-12s | %s |", "response:", results.joinToString(" | ")))
    println(String.format("%-12s | %s |", "delta_time:", times.joinToString(" | ")))
    println("------------------------------------------------")
}
package org.locallback.natives

class InitLiblocall {

    init {
        System.loadLibrary("liblocall")
    }

    external fun initLiblocall()
}
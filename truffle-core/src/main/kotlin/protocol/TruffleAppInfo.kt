package com.wafflestudio.truffle.sdk.core.protocol

object TruffleAppInfo {
    val runtime = TruffleRuntime()

    data class TruffleRuntime(
        val name: String = "Java",
        val version: String = System.getProperty("java.version")
    )
}

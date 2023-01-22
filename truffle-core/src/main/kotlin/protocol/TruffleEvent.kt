package com.wafflestudio.truffle.sdk.core.protocol

data class TruffleEvent(
    val version: String = TruffleVersion.V1,
    val app: TruffleApp,
    val runtime: TruffleRuntime,
    val exception: TruffleException,
)

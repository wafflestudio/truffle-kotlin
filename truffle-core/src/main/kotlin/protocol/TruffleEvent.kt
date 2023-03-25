package com.wafflestudio.truffle.sdk.core.protocol

data class TruffleEvent(
    val version: String = TruffleVersion.V1,
    val runtime: TruffleRuntime = TruffleRuntime,
    val level: TruffleLevel,
    val exception: TruffleException?,
    val message: String?,
)

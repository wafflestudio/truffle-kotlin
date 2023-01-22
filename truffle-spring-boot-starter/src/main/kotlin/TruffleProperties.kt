package com.wafflestudio.truffle.sdk

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("truffle.client")
data class TruffleProperties(
    val name: String,
    val phase: String,
    val apiKey: String,
)

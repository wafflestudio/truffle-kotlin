package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import org.springframework.web.client.RestClient

internal class Hub(
    apiKey: String,
    restClientBuilder: RestClient.Builder? = null,
) : IHub {
    private val client: TruffleClient = DefaultTruffleClient(
        apiKey = apiKey,
        restClientBuilder = restClientBuilder ?: RestClient.builder(),
    )

    override fun captureEvent(truffleEvent: TruffleEvent) {
        client.sendEvent(truffleEvent)
    }
}

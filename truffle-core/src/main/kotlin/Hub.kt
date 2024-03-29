package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import org.springframework.web.reactive.function.client.WebClient

internal class Hub(
    truffleOptions: TruffleOptions,
    webClientBuilder: WebClient.Builder? = null,
) : IHub {
    private val client: TruffleClient = DefaultTruffleClient(
        apiKey = truffleOptions.apiKey,
        webClientBuilder = webClientBuilder ?: WebClient.builder(),
    )

    override fun captureEvent(truffleEvent: TruffleEvent) {
        client.sendEvent(truffleEvent)
    }
}

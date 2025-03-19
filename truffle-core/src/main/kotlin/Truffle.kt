package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import org.springframework.web.client.RestClient

object Truffle {
    private lateinit var hub: IHub

    internal object HubAdapter : IHub {
        override fun captureEvent(truffleEvent: TruffleEvent) {
            Truffle.captureEvent(truffleEvent)
        }
    }

    @Synchronized fun init(apiKey: String): IHub {
        if (::hub.isInitialized) {
            return hub
        }

        validateConfig(apiKey)

        this.hub = Hub(apiKey)
        return HubAdapter
    }

    private fun captureEvent(truffleEvent: TruffleEvent) {
        hub.captureEvent(truffleEvent)
    }

    private fun validateConfig(apiKey: String) {
        if (apiKey.isBlank()) {
            throw IllegalArgumentException("Truffle API key is blank")
        }
    }
}

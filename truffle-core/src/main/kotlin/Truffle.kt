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

    // for modules without access RestClient.Builder
    fun init(apiKey: String): IHub {
        return init(apiKey, null)
    }

    @Synchronized fun init(apiKey: String, restClientBuilder: RestClient.Builder? = null): IHub {
        if (::hub.isInitialized) {
            return hub
        }

        validateConfig(apiKey)

        this.hub = Hub(apiKey, restClientBuilder)
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

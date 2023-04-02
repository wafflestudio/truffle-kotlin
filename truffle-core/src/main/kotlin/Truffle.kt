package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import org.springframework.web.reactive.function.client.WebClient

object Truffle {
    private lateinit var hub: IHub

    internal object HubAdapter : IHub {
        override fun captureEvent(truffleEvent: TruffleEvent) {
            Truffle.captureEvent(truffleEvent)
        }
    }

    // for modules without access WebClient.Builder
    fun init(truffleOptions: TruffleOptions): IHub {
        return init(truffleOptions, null)
    }

    @Synchronized fun init(truffleOptions: TruffleOptions, webClientBuilder: WebClient.Builder? = null): IHub {
        if (::hub.isInitialized) {
            return hub
        }

        validateConfig(truffleOptions)

        this.hub = Hub(truffleOptions, webClientBuilder)
        return HubAdapter
    }

    private fun captureEvent(truffleEvent: TruffleEvent) {
        hub.captureEvent(truffleEvent)
    }

    private fun validateConfig(truffleOptions: TruffleOptions) {
        if (truffleOptions.apiKey.isBlank()) {
            throw IllegalArgumentException("Truffle API key is blank")
        }
    }
}

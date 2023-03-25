package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient

object Truffle {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var hub: IHub

    @Synchronized fun isInitialized(): Boolean {
        return ::hub.isInitialized
    }

    // for modules without access WebClient.Builder
    fun init(truffleOptions: TruffleOptions): IHub {
        return init(truffleOptions, null)
    }

    @Synchronized fun init(truffleOptions: TruffleOptions, webClientBuilder: WebClient.Builder? = null): IHub {
        if (isInitialized()) {
            logger.warn("Truffle is already initialized. Previous configuration will be used.")
            return hub
        }

        validateConfig(truffleOptions)

        val hub = Hub(truffleOptions, webClientBuilder)
        this.hub = hub
        return hub
    }

    fun captureEvent(truffleEvent: TruffleEvent) {
        hub.captureEvent(truffleEvent)
    }

    private fun validateConfig(truffleOptions: TruffleOptions) {
        if (truffleOptions.apiKey.isBlank()) {
            throw IllegalArgumentException("Truffle API key is blank")
        }
        if (truffleOptions.phase.isBlank()) {
            throw IllegalArgumentException("Truffle phase is blank")
        }
    }
}

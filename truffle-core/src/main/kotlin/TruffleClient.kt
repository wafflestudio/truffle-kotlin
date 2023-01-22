package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleApp
import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import com.wafflestudio.truffle.sdk.core.protocol.TruffleException
import com.wafflestudio.truffle.sdk.core.protocol.TruffleRuntime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.Duration
import java.util.concurrent.Executors

interface TruffleClient {
    fun sendEvent(ex: Throwable)
}

class DefaultTruffleClient(
    name: String,
    phase: String,
    apiKey: String,
    webClientBuilder: WebClient.Builder,
) : TruffleClient {
    private val events = MutableSharedFlow<TruffleEvent>(extraBufferCapacity = 10)

    private val logger = LoggerFactory.getLogger(javaClass)

    private val truffleApp = TruffleApp(name, phase)
    private val truffleRuntime = TruffleRuntime(name = "Java", version = System.getProperty("java.version"))

    init {
        val coroutineScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
        val webClient = webClientBuilder
            .baseUrl("https://truffle-api.wafflestudio.com")
            .defaultHeader("x-api-key", apiKey)
            .build()

        coroutineScope.launch(SupervisorJob()) {
            events.collect {
                runCatching {
                    webClient
                        .post()
                        .uri("/events")
                        .bodyValue(it)
                        .retrieve()
                        .bodyToMono<Unit>()
                        .timeout(Duration.ofSeconds(1))
                        .awaitSingle()
                }.getOrElse {
                    logger.warn("Failed to request to truffle server", it)
                }
            }
        }
    }

    override fun sendEvent(ex: Throwable) {
        events.tryEmit(
            TruffleEvent(
                app = truffleApp,
                runtime = truffleRuntime,
                exception = TruffleException(ex),
            )
        )
    }
}

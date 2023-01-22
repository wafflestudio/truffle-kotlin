package com.wafflestudio.truffle.sdk.reactive

import com.wafflestudio.truffle.sdk.core.TruffleClient
import org.springframework.core.annotation.Order
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Order(-2)
class TruffleWebExceptionHandler(
    private val truffleClient: TruffleClient,
) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is ResponseStatusException) {
            truffleClient.sendEvent(ex)
        }

        return Mono.error(ex)
    }
}

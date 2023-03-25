package com.wafflestudio.truffle.sdk.reactive

import com.wafflestudio.truffle.sdk.core.IHub
import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import com.wafflestudio.truffle.sdk.core.protocol.TruffleException
import com.wafflestudio.truffle.sdk.core.protocol.TruffleLevel
import org.springframework.core.annotation.Order
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Order(-2)
class TruffleWebExceptionHandler(private val hub: IHub) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is ResponseStatusException) {
            hub.captureEvent(
                TruffleEvent(
                    level = TruffleLevel.FATAL,
                    exception = TruffleException(ex),
                )
            )
        }

        return Mono.error(ex)
    }
}

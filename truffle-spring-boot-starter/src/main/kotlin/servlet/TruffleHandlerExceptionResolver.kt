package com.wafflestudio.truffle.sdk.servlet

import com.wafflestudio.truffle.sdk.core.IHub
import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import com.wafflestudio.truffle.sdk.core.protocol.TruffleException
import com.wafflestudio.truffle.sdk.core.protocol.TruffleLevel
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.connector.ClientAbortException
import org.springframework.core.annotation.Order
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

@Order(1)
class TruffleHandlerExceptionResolver(private val hub: IHub) : HandlerExceptionResolver {
    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception,
    ): ModelAndView? {
        if (ex !is ResponseStatusException && ex !is ClientAbortException) {
            hub.captureEvent(
                TruffleEvent(
                    level = TruffleLevel.FATAL,
                    exception = TruffleException(ex),
                )
            )
        }

        return null
    }
}

package com.wafflestudio.truffle.sdk.servlet

import com.wafflestudio.truffle.sdk.core.TruffleClient
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@Order(-2)
class TruffleHandlerExceptionResolver(
    private val truffleClient: TruffleClient,
) : HandlerExceptionResolver {
    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception,
    ): ModelAndView? {
        if (ex !is ResponseStatusException) {
            truffleClient.sendEvent(ex)
        }

        return null
    }
}

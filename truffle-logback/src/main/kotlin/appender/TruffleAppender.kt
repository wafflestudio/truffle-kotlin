package com.wafflestudio.truffle.sdk.logback

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ThrowableProxy
import ch.qos.logback.core.UnsynchronizedAppenderBase
import com.wafflestudio.truffle.sdk.core.Truffle
import com.wafflestudio.truffle.sdk.core.TruffleOptions
import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent
import com.wafflestudio.truffle.sdk.core.protocol.TruffleException
import com.wafflestudio.truffle.sdk.core.protocol.TruffleLevel

class TruffleAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {
    lateinit var options: TruffleOptions

    override fun start() {
        if (!Truffle.isInitialized()) {
            Truffle.init(options)
        }
        super.start()
    }

    override fun append(eventObject: ILoggingEvent) {
        if (eventObject.level.isGreaterOrEqual(options.minimumLevel) &&
            !eventObject.loggerName.startsWith("com.wafflestudio.truffle.sdk")
        ) {
            val truffleEvent = createEvent(eventObject)
            Truffle.captureEvent(truffleEvent)
        }
    }

    private fun createEvent(eventObject: ILoggingEvent): TruffleEvent {
        val exception = eventObject.throwableProxy?.let {
            TruffleException((it as ThrowableProxy).throwable)
        }

        return TruffleEvent(
            level = TruffleLevel.from(eventObject.level),
            message = eventObject.formattedMessage,
            exception = exception,
        )
    }
}

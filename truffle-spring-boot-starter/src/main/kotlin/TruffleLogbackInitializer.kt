package com.wafflestudio.truffle.sdk

import ch.qos.logback.classic.LoggerContext
import com.wafflestudio.truffle.sdk.logback.TruffleAppender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType
import ch.qos.logback.classic.Logger as LogbackLogger

class TruffleLogbackInitializer(
    private val truffleProperties: TruffleProperties,
) : GenericApplicationListener {
    override fun supportsEventType(eventType: ResolvableType): Boolean {
        return eventType.rawClass?.let { ContextRefreshedEvent::class.java.isAssignableFrom(it) } ?: false
    }

    override fun onApplicationEvent(event: ApplicationEvent) {
        val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as LogbackLogger

        if (!isTruffleAppenderRegistered(rootLogger)) {
            val truffleAppender = TruffleAppender()
            truffleAppender.name = "TRUFFLE_APPENDER"
            truffleAppender.context = LoggerFactory.getILoggerFactory() as LoggerContext
            truffleAppender.apiKey = truffleProperties.apiKey

            truffleAppender.start()
            rootLogger.addAppender(truffleAppender)
        }
    }

    private fun isTruffleAppenderRegistered(logger: LogbackLogger): Boolean {
        val iterator = logger.iteratorForAppenders()
        while (iterator.hasNext()) {
            if (iterator.next() is TruffleAppender) {
                return true
            }
        }
        return false
    }
}

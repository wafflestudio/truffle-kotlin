package com.wafflestudio.truffle.sdk

import ch.qos.logback.classic.LoggerContext
import com.wafflestudio.truffle.sdk.core.IHub
import com.wafflestudio.truffle.sdk.core.Truffle
import com.wafflestudio.truffle.sdk.reactive.TruffleWebExceptionHandler
import com.wafflestudio.truffle.sdk.servlet.TruffleHandlerExceptionResolver
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.WebExceptionHandler
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableConfigurationProperties(TruffleProperties::class)
@Configuration
class TruffleAutoConfiguration {
    @Bean
    fun truffleHub(properties: TruffleProperties, webClientBuilder: WebClient.Builder): IHub {
        return Truffle.init(properties, webClientBuilder)
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @Configuration
    class TruffleServletConfiguration {
        @Bean
        fun truffleHandlerExceptionResolver(truffleHub: IHub): HandlerExceptionResolver {
            return TruffleHandlerExceptionResolver(truffleHub)
        }
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    class TruffleReactiveConfiguration {
        @Bean
        fun truffleWebExceptionHandler(truffleHub: IHub): WebExceptionHandler {
            return TruffleWebExceptionHandler(truffleHub)
        }
    }

    @ConditionalOnClass(LoggerContext::class)
    @ConditionalOnProperty(value = ["truffle.logback.enabled"], havingValue = "true", matchIfMissing = true)
    @Configuration
    class TruffleLogbackConfiguration {
        @Bean
        fun truffleLogbackInitializer(truffleProperties: TruffleProperties): TruffleLogbackInitializer {
            return TruffleLogbackInitializer(truffleProperties)
        }
    }
}

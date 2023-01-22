package com.wafflestudio.truffle.sdk

import com.wafflestudio.truffle.sdk.core.DefaultTruffleClient
import com.wafflestudio.truffle.sdk.core.TruffleClient
import com.wafflestudio.truffle.sdk.reactive.TruffleWebExceptionHandler
import com.wafflestudio.truffle.sdk.servlet.TruffleHandlerExceptionResolver
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
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @Configuration
    class TruffleServletConfiguration {
        @Bean
        fun truffleHandlerExceptionResolver(truffleClient: TruffleClient): HandlerExceptionResolver {
            return TruffleHandlerExceptionResolver(truffleClient)
        }
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    class TruffleReactiveConfiguration {
        @Bean
        fun truffleWebExceptionHandler(truffleClient: TruffleClient): WebExceptionHandler {
            return TruffleWebExceptionHandler(truffleClient)
        }
    }

    @Bean
    fun truffleClient(properties: TruffleProperties, webClientBuilder: WebClient.Builder): TruffleClient =
        DefaultTruffleClient(
            name = properties.name,
            phase = properties.phase,
            apiKey = properties.apiKey,
            webClientBuilder = webClientBuilder,
        )
}

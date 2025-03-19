package com.wafflestudio.truffle.sdk

import com.wafflestudio.truffle.sdk.core.IHub
import com.wafflestudio.truffle.sdk.core.Truffle
import com.wafflestudio.truffle.sdk.reactive.TruffleWebExceptionHandler
import com.wafflestudio.truffle.sdk.servlet.TruffleHandlerExceptionResolver
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.WebExceptionHandler
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableConfigurationProperties(TruffleProperties::class)
@Configuration
class TruffleAutoConfiguration {
    @Bean
    fun truffleHub(properties: TruffleProperties): IHub {
        return Truffle.init(properties.apiKey)
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(value = ["truffle.auto.enabled"], havingValue = "true")
    @Configuration
    class TruffleServletConfiguration {
        @Bean
        fun truffleHandlerExceptionResolver(truffleHub: IHub): HandlerExceptionResolver {
            return TruffleHandlerExceptionResolver(truffleHub)
        }
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnProperty(value = ["truffle.auto.enabled"], havingValue = "true")
    @Configuration
    class TruffleReactiveConfiguration {
        @Bean
        fun truffleWebExceptionHandler(truffleHub: IHub): WebExceptionHandler {
            return TruffleWebExceptionHandler(truffleHub)
        }
    }
}

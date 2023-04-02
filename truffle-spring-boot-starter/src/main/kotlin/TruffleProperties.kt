package com.wafflestudio.truffle.sdk

import ch.qos.logback.classic.Level
import com.wafflestudio.truffle.sdk.core.TruffleOptions
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("truffle.client")
data class TruffleProperties(
    /**
     * Truffle 서버에서 애플리케이션의 요청이 유효한지 검증하는 데에 사용하는 API key.
     *
     * 외부에 공개되지 않도록 주의해 관리해야 합니다.
     */
    override var apiKey: String,

    /**
     * truffle logback 사용 시 이벤트를 전송할 최소 로그 레벨.
     */
    override var minimumLevel: Level = Level.ERROR,
) : TruffleOptions()

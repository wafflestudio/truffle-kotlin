package com.wafflestudio.truffle.sdk

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("truffle.client")
data class TruffleProperties(
    /**
     * Truffle 이 식별하는 애플리케이션의 이름.
     *
     * 에러 리포트에 사용되며 Truffle 서버에 등록된 이름과 일치하는 정확한 애플리케이션 이름이 사용되어야 합니다.
     */
    val name: String,
    /**
     * 애플리케이션의 환경을 구분하는 이름.
     *
     * 에러 리포트에 사용되며 `"prod"`, `"dev"`, `"local"` 등이 사용될 수 있습니다.
     * `"local"` 또는 `"test"`가 사용되는 경우, Truffle SDK 는 Truffle 서버로 요청을 전송하지 않습니다.
     */
    val phase: String,
    /**
     * Truffle 서버에서 애플리케이션의 요청이 유효한지 검증하는 데에 사용하는 API key.
     *
     * 외부에 공개되지 않도록 주의해 관리해야 합니다.
     */
    val apiKey: String,
)

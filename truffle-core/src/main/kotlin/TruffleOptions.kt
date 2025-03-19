package com.wafflestudio.truffle.sdk.core

open class TruffleOptions {
    /**
     * Truffle 서버에서 애플리케이션의 요청이 유효한지 검증하는 데에 사용하는 API key.
     *
     * 외부에 공개되지 않도록 주의해 관리해야 합니다.
     */
    open lateinit var apiKey: String
}

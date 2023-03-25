package com.wafflestudio.truffle.sdk.core

import com.wafflestudio.truffle.sdk.core.protocol.TruffleEvent

interface IHub {
    fun captureEvent(truffleEvent: TruffleEvent)
}

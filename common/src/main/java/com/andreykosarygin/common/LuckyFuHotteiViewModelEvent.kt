package com.andreykosarygin.common

open class LuckyFuHotteiViewModelEvent<EVENT>(
    private val event: EVENT
) {
    open fun use(doEvent: (EVENT) -> Unit) {
        doEvent(event)
    }
}
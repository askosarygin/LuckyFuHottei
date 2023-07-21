package com.andreykosarygin.common

import java.util.concurrent.atomic.AtomicBoolean

open class LuckyFuHotteiViewModelSingleLifeEvent<EVENT>(
    private val event: EVENT
) : LuckyFuHotteiViewModelEvent<EVENT>(event) {
    private val processed = AtomicBoolean(false)

    override fun use(doEvent: (EVENT) -> Unit) {
        if (!processed.getAndSet(true)) {
            super.use(doEvent)
        }
    }
}
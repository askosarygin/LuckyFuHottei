package com.andreykosarygin.game_domain

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.Level

interface Interactor {
    suspend fun changeBalance(balanceInfo: BalanceInfo): Boolean

    suspend fun getBalance(): BalanceInfo

    suspend fun levelIsOpen(level: Level): Boolean

    suspend fun openLevel(level: Level): Boolean
}
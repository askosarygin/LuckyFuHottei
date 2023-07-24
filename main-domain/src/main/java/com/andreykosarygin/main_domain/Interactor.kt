package com.andreykosarygin.main_domain

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.TreasureInfo
import java.util.Date

interface Interactor {
    suspend fun loadWelcomeBonusTimer(): Date

    suspend fun saveWelcomeBonusTimer(date: Date): Boolean

    suspend fun changeBalance(balanceInfo: BalanceInfo): Boolean

    suspend fun getBalance(): BalanceInfo

    suspend fun isTreasureBought(treasureInfo: TreasureInfo): Boolean

    suspend fun buyTreasure(treasureInfo: TreasureInfo): Boolean
}
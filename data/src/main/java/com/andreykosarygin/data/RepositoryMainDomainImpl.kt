package com.andreykosarygin.data

import com.andreykosarygin.data.appdata.AppData
import com.andreykosarygin.main_domain.Repository

class RepositoryMainDomainImpl(
    private val appData: AppData
): Repository {
    override suspend fun loadWelcomeBonusTimer(): Long =
        appData.getWelcomeBonusTime()

    override suspend fun saveWelcomeBonusTimer(time: Long): Boolean =
        appData.saveWelcomeBonusTime(time)

    override suspend fun changeBalance(earnedPoints: Int): Boolean =
        appData.changeBalance(earnedPoints)

    override suspend fun getBalance(): Int =
        appData.getBalance()

    override suspend fun isTreasureBought(index: Int): Boolean =
        appData.isTreasureBought(index)

    override suspend fun buyTreasure(index: Int): Boolean =
        appData.buyTreasure(index)
}
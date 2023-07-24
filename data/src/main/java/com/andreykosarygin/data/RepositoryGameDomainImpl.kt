package com.andreykosarygin.data

import com.andreykosarygin.data.appdata.AppData
import com.andreykosarygin.game_domain.Repository

class RepositoryGameDomainImpl(
    private val appData: AppData
): Repository {
    override suspend fun changeBalance(earnedPoints: Int): Boolean =
        appData.changeBalance(earnedPoints)

    override suspend fun getBalance(): Int =
        appData.getBalance()

    override suspend fun levelIsOpen(index: Int): Boolean =
        appData.levelIsOpen(index)

    override suspend fun openLevel(index: Int): Boolean =
        appData.openLevel(index)

}
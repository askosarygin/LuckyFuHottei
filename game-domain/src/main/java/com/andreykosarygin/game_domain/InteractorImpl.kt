package com.andreykosarygin.game_domain

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.Level
import com.andreykosarygin.game_domain.usecases.ChangeBalanceUseCase
import com.andreykosarygin.game_domain.usecases.GetBalanceUseCase
import com.andreykosarygin.game_domain.usecases.IsLevelOpenUseCase
import com.andreykosarygin.game_domain.usecases.OpenLevelUseCase

class InteractorImpl(
    private val changeBalanceUseCase: ChangeBalanceUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val isLevelOpenUseCase: IsLevelOpenUseCase,
    private val openLevelOpenUseCase: OpenLevelUseCase
) : Interactor {
    override suspend fun changeBalance(balanceInfo: BalanceInfo): Boolean =
        changeBalanceUseCase.execute(balanceInfo)

    override suspend fun getBalance(): BalanceInfo =
        getBalanceUseCase.execute()

    override suspend fun levelIsOpen(level: Level): Boolean =
        isLevelOpenUseCase.execute(level)

    override suspend fun openLevel(level: Level): Boolean =
        openLevelOpenUseCase.execute(level)

}
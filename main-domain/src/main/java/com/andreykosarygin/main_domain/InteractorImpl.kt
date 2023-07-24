package com.andreykosarygin.main_domain

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.common.TreasureInfo
import com.andreykosarygin.main_domain.usecases.BuyTreasureUseCase
import com.andreykosarygin.main_domain.usecases.ChangeBalanceUseCase
import com.andreykosarygin.main_domain.usecases.GetBalanceUseCase
import com.andreykosarygin.main_domain.usecases.IsTreasureBoughtUseCase
import com.andreykosarygin.main_domain.usecases.LoadWelcomeBonusTimerUseCase
import com.andreykosarygin.main_domain.usecases.SaveWelcomeBonusTimerUseCase
import java.util.Date

class InteractorImpl(
    private val loadWelcomeBonusTimerUseCase: LoadWelcomeBonusTimerUseCase,
    private val saveWelcomeBonusTimerUseCase: SaveWelcomeBonusTimerUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val buyTreasureUseCase: BuyTreasureUseCase,
    private val isTreasureBoughtUseCase: IsTreasureBoughtUseCase
): Interactor {
    override suspend fun loadWelcomeBonusTimer(): Date =
        loadWelcomeBonusTimerUseCase.execute()


    override suspend fun saveWelcomeBonusTimer(date: Date): Boolean =
        saveWelcomeBonusTimerUseCase.execute(date)

    override suspend fun changeBalance(balanceInfo: BalanceInfo): Boolean =
        changeBalanceUseCase.execute(balanceInfo)

    override suspend fun getBalance(): BalanceInfo =
        getBalanceUseCase.execute()

    override suspend fun isTreasureBought(treasureInfo: TreasureInfo): Boolean =
        isTreasureBoughtUseCase.execute(treasureInfo)

    override suspend fun buyTreasure(treasureInfo: TreasureInfo): Boolean =
        buyTreasureUseCase.execute(treasureInfo)
}
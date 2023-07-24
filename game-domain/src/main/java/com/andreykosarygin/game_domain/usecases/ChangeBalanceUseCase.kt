package com.andreykosarygin.game_domain.usecases

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.game_domain.Repository

class ChangeBalanceUseCase(
    private val repository: Repository
) {
    suspend fun execute(balanceInfo: BalanceInfo): Boolean =
        repository.changeBalance(balanceInfo.value)
}
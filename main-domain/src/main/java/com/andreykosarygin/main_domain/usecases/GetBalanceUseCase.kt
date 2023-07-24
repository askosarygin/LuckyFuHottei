package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.main_domain.Repository

class GetBalanceUseCase(
    private val repository: Repository
) {
    suspend fun execute(): BalanceInfo {
        val balance = repository.getBalance()
        return BalanceInfo(balance)
    }
}
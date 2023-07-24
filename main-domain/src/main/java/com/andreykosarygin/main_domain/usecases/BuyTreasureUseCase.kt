package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.common.TreasureInfo
import com.andreykosarygin.main_domain.Repository

class BuyTreasureUseCase(
    private val repository: Repository
) {
    suspend fun execute(treasureInfo: TreasureInfo): Boolean {
        val treasureIsBought = repository.isTreasureBought(treasureInfo.index)
        return if (!treasureIsBought) {
            val balance = repository.getBalance()
            if (balance - treasureInfo.price >= 0) {
                repository.changeBalance(-(treasureInfo.price))
                repository.buyTreasure(treasureInfo.index)
                true
            } else {
                false
            }
        } else {
            false
        }
    }
}
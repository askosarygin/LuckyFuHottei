package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.common.TreasureInfo
import com.andreykosarygin.main_domain.Repository

class IsTreasureBoughtUseCase(
    private val repository: Repository
) {
    suspend fun execute(treasureInfo: TreasureInfo): Boolean =
        repository.isTreasureBought(treasureInfo.index)
}
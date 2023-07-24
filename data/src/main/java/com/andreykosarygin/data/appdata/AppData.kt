package com.andreykosarygin.data.appdata

interface AppData {
    suspend fun changeBalance(earnedPoints: Int): Boolean
    suspend fun getBalance(): Int

    suspend fun isTreasureBought(index: Int): Boolean
    suspend fun buyTreasure(index: Int): Boolean

    suspend fun levelIsOpen(index: Int): Boolean
    suspend fun openLevel(index: Int): Boolean

    suspend fun saveWelcomeBonusTime(timeInMillis: Long): Boolean
    suspend fun getWelcomeBonusTime(): Long

    companion object {
        const val KEY_APP_DATA_SHARED_PREFERENCES = "key_app_data_shared_preferences"
    }
}
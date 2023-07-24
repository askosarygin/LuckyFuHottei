package com.andreykosarygin.main_domain

interface Repository {
    suspend fun loadWelcomeBonusTimer(): Long

    suspend fun saveWelcomeBonusTimer(time: Long): Boolean

    suspend fun changeBalance(earnedPoints: Int): Boolean

    suspend fun getBalance(): Int

    suspend fun isTreasureBought(index: Int): Boolean

    suspend fun buyTreasure(index: Int): Boolean
}
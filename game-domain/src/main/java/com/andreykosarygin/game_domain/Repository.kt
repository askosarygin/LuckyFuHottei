package com.andreykosarygin.game_domain

interface Repository {

    suspend fun changeBalance(earnedPoints: Int): Boolean

    suspend fun getBalance(): Int

    suspend fun levelIsOpen(index: Int): Boolean

    suspend fun openLevel(index: Int): Boolean
}
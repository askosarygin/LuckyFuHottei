package com.andreykosarygin.luckyfuhottei

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.andreykosarygin.data.RepositoryMainDomainImpl
import com.andreykosarygin.data.appdata.AppDataImpl
import com.andreykosarygin.main_domain.InteractorImpl
import com.andreykosarygin.main_domain.usecases.BuyTreasureUseCase
import com.andreykosarygin.main_domain.usecases.ChangeBalanceUseCase
import com.andreykosarygin.main_domain.usecases.GetBalanceUseCase
import com.andreykosarygin.main_domain.usecases.IsTreasureBoughtUseCase
import com.andreykosarygin.main_domain.usecases.LoadWelcomeBonusTimerUseCase
import com.andreykosarygin.main_domain.usecases.SaveWelcomeBonusTimerUseCase

class MainApp : Application() {
    private val sharedPreferencesName = "lucky_fu_hottei_shared_preferences"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appDataImpl: AppDataImpl
    private lateinit var repositoryMainDomainImpl: RepositoryMainDomainImpl
    lateinit var interactorImplMainDomain: InteractorImpl

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = this.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        appDataImpl = AppDataImpl(sharedPreferences)

        repositoryMainDomainImpl = RepositoryMainDomainImpl(appDataImpl)
        interactorImplMainDomain = InteractorImpl(
            LoadWelcomeBonusTimerUseCase(repositoryMainDomainImpl),
            SaveWelcomeBonusTimerUseCase(repositoryMainDomainImpl),
            ChangeBalanceUseCase(repositoryMainDomainImpl),
            GetBalanceUseCase(repositoryMainDomainImpl),
            BuyTreasureUseCase(repositoryMainDomainImpl),
            IsTreasureBoughtUseCase(repositoryMainDomainImpl)
        )
    }
}
package me.estudos.githubrepositories

import android.app.Application
import me.estudos.githubrepositories.data.di.DataModule
import me.estudos.githubrepositories.domain.di.DomainModule
import me.estudos.githubrepositories.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}

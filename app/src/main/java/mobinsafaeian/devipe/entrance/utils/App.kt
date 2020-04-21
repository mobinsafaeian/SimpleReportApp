package mobinsafaeian.devipe.entrance.utils


import androidx.multidex.MultiDexApplication
import mobinsafaeian.devipe.entrance.di.applicationModule
import org.koin.android.ext.android.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin For DI
        startKoin(this , listOf(applicationModule))
    }
}
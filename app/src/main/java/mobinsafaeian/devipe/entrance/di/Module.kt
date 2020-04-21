package mobinsafaeian.devipe.entrance.di

import mobinsafaeian.devipe.entrance.model.Const
import mobinsafaeian.devipe.entrance.model.connections.ApiInterface
import mobinsafaeian.devipe.entrance.model.connections.RetrofitBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module.module


val applicationModule = module(override = true) {

    /**
     * ---------
     * Single Objects Section
     * that generated single objects
     * ---------
     */

    single<OkHttpClient> {
        RetrofitBuilder.getOkHttpClient()
    }

    single<ApiInterface> {
        RetrofitBuilder.create(Const.BASE_URL , get())
    }

}
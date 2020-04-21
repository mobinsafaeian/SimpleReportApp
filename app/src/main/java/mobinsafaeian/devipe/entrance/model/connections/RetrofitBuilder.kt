package mobinsafaeian.devipe.entrance.model.connections

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    companion object {
        object RetrofitObj{
            lateinit var retrofit:Retrofit
        }
        fun create(BASE_URL:String , okHttpClient: OkHttpClient): ApiInterface {

            RetrofitObj.retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()

            return RetrofitObj.retrofit.create(
                ApiInterface::class.java)
        }

        fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1 , TimeUnit.MINUTES)
            .readTimeout(1 , TimeUnit.MINUTES)
            .writeTimeout(1 , TimeUnit.MINUTES)
            .build()
    }
}
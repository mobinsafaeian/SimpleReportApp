package mobinsafaeian.devipe.entrance.model.connections

import io.reactivex.Single
import mobinsafaeian.devipe.entrance.model.connections.responses.MainResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    // Getting the main list api method
    @GET("/v1/deqr/show")
    fun fetchList() : Single<Response<MainResponse>>

}
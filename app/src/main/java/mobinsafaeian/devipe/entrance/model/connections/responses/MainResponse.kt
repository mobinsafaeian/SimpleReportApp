package mobinsafaeian.devipe.entrance.model.connections.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainResponse {

    @SerializedName("rollCalls")
    @Expose
    lateinit var list: ArrayList<RollCall>

}
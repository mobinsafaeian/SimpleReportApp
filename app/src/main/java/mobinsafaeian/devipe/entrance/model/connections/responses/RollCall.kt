package mobinsafaeian.devipe.entrance.model.connections.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RollCall {

    @SerializedName("username")
    @Expose
    var username: String = ""

    @SerializedName("entrance")
    @Expose
    var entrance: String = ""

    @SerializedName("exit")
    @Expose
    var exit: String = ""

}
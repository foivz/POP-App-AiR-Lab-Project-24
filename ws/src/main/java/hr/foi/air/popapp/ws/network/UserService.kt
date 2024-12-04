package hr.foi.air.popapp.ws.network

import hr.foi.air.popapp.core.network.models.SuccessfulResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserService {
    @PATCH("user/{userId}/store")
    fun assignStore(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: Int,
        @Body storeName: String
    ): Call<SuccessfulResponseBody<Any?>>
}

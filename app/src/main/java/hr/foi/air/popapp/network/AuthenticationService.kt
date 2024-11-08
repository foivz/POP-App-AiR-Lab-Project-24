package hr.foi.air.popapp.network

import hr.foi.air.popapp.network.models.RegistrationBody
import hr.foi.air.popapp.network.models.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("auth/register")
    fun registerUser(@Body registerBody: RegistrationBody): Call<ResponseBody>
}

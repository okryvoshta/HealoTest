package app.healo.test.model.network

import app.healo.test.model.db.Message
import retrofit2.Call
import retrofit2.http.GET

interface NetworkAPI {

    @GET("/posts")
    fun getMessages(): Call<List<Message>>
}
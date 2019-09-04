package app.healo.test.model.network

import androidx.core.util.Consumer
import app.healo.test.R
import app.healo.test.helper.App
import app.healo.test.model.db.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkLayer(private val networkAPI: NetworkAPI) : INetworkLayer {

    private var mCall: Call<List<Message>>? = null

    override fun loadDataFromNetwork(data: Consumer<List<Message>>, onFailed: Consumer<String>) {
        mCall = networkAPI.getMessages()
        mCall?.enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) response.body()?.let { data.accept(it) }
                else onFailed.accept(App.getInstance().getString(R.string.network_load_error, response.errorBody().toString()))
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                onFailed.accept(t.message)
            }
        })
    }

    override fun onDestroy() {
        mCall?.cancel()
    }
}
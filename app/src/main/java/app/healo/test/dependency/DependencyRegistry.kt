package app.healo.test.dependency

import app.healo.test.helper.Config
import app.healo.test.model.ModelLayer
import app.healo.test.model.db.DatabaseLayer
import app.healo.test.model.db.IDatabaseLayer
import app.healo.test.model.network.INetworkLayer
import app.healo.test.model.network.NetworkAPI
import app.healo.test.model.network.NetworkLayer
import app.healo.test.presenter.Presenter
import app.healo.test.view.MainActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyRegistry  {

    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val mNetworkAPI = mRetrofit.create(NetworkAPI::class.java)

    private val databaseLayer: IDatabaseLayer = DatabaseLayer()
    private val networkLayer: INetworkLayer = NetworkLayer(mNetworkAPI)

    private val modelLayer = ModelLayer(databaseLayer, networkLayer)

    fun inject(activity: MainActivity) {
        activity.configure(Presenter(activity, modelLayer))
    }

}

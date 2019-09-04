package app.healo.test.network

import androidx.core.util.Consumer
import app.healo.test.helper.Config
import app.healo.test.model.db.Message
import app.healo.test.model.network.NetworkAPI
import app.healo.test.model.network.NetworkLayer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class NetworkLayerTest {
    private lateinit var mockNetworkLayer: NetworkLayer
    private lateinit var realNetworkLayer: NetworkLayer
    private lateinit var mockWebServer: MockWebServer
    private val port = 8181

    @Before
    fun initWebServer() {
        mockWebServer = MockWebServer(port)
        mockNetworkLayer = NetworkLayer(Retrofit.Builder()
            .baseUrl("http://localhost:$port")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NetworkAPI::class.java))
        realNetworkLayer = NetworkLayer(Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NetworkAPI::class.java))
    }

    @Test
    fun realQueryTest() {
        val latch = CountDownLatch(1)
        var data: List<Message>? = null
        var failMessage: String? = null
        var requestSuccess: Boolean? = null

        realNetworkLayer.loadDataFromNetwork(Consumer {
            data = it
            requestSuccess = true
            latch.countDown()
        }, Consumer {
            failMessage = it
            requestSuccess = false
            latch.countDown()
        })

        latch.await(10, TimeUnit.SECONDS)

        Assert.assertNotNull("Request was not executed", requestSuccess)

        if (requestSuccess!!) {
            Assert.assertNotNull("Empty array list", data)

            Assert.assertTrue("Wrong array size", data!!.size == 100)
            val ids: MutableSet<Int> = HashSet()
            for (message in data!!) {
                Assert.assertFalse("Duplicated id ${message.id}", ids.contains(message.id))
                ids.add(message.id)
                Assert.assertTrue("Some message has wrong id", message.id > 0)
                Assert.assertTrue("Some message has wrong userId", message.userId > 0)
                Assert.assertTrue("Some title is empty", message.title!!.isNotEmpty())
                Assert.assertTrue("Some body is empty", message.body!!.isNotEmpty())
            }
        } else {
            Assert.fail("Request execution is failed: $failMessage")
        }
    }

    @Test
    fun mockQueryNotJsonTest() {
        val latch = CountDownLatch(1)
        var requestSuccess: Boolean? = null

        mockWebServer.setResponse("Some not json string")
        mockNetworkLayer.loadDataFromNetwork(Consumer {
            requestSuccess = true
            latch.countDown()
        }, Consumer {
            requestSuccess = false
            latch.countDown()
        })

        latch.await(10, TimeUnit.SECONDS)

        Assert.assertNotNull("Request was not executed", requestSuccess)
        Assert.assertFalse("Correctly handled not json string", requestSuccess!!)
    }

    @Test
    fun mockQueryWrongJsonTest() {
        val latch = CountDownLatch(1)
        var requestSuccess: Boolean? = null

        mockWebServer.setResponse("{}")
        mockNetworkLayer.loadDataFromNetwork(Consumer {
            requestSuccess = true
            latch.countDown()
        }, Consumer {
            requestSuccess = false
            latch.countDown()
        })

        latch.await(10, TimeUnit.SECONDS)

        Assert.assertNotNull("Request was not executed", requestSuccess)
        Assert.assertFalse("Correctly handled not json string", requestSuccess!!)
    }

    @After
    fun finish() {
        mockWebServer.stop()
    }
}

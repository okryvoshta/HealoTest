package app.healo.test.model

import androidx.core.util.Consumer
import app.healo.test.TestData
import app.healo.test.model.db.IDatabaseLayer
import app.healo.test.model.db.Message
import app.healo.test.model.network.INetworkLayer
import org.junit.Assert.*
import org.junit.Test

class ModelLayerTest {

    @Test
    fun getDataSuccess() {
        var stackTraceIndex = 0
        val modelLayer = ModelLayer(object : IDatabaseLayer {
            override fun getDataFromDB(onDataLoaded: Consumer<List<Message>>) {
                assertTrue("getDataFromDB is called in wrong flow", stackTraceIndex == 0)
                stackTraceIndex++
            }

            override fun saveDataToDB(data: List<Message>) {
                assertTrue("saveDataToDB is called in wrong flow", stackTraceIndex == 2)
                stackTraceIndex++
                assertEquals("Wrong data for saving to DB", TestData.messagesList, data)
            }

            override fun onDestroy() {
                fail("onDestroy method is called during success operation")
            }
        }, object : INetworkLayer {
            override fun loadDataFromNetwork(
                data: Consumer<List<Message>>,
                onFailed: Consumer<String>
            ) {
                assertTrue("loadDataFromNetwork is called in wrong flow", stackTraceIndex == 1)
                stackTraceIndex++
                data.accept(TestData.messagesList)
            }

            override fun onDestroy() {
                fail("onDestroy method is called during success operation")
            }
        })

        modelLayer.getData(Consumer {

        }, Consumer {
            fail("Fail in success method")
        })
    }

    @Test
    fun getDataFailed() {
        var stackTraceIndex = 0
        val modelLayer = ModelLayer(object : IDatabaseLayer {
            override fun getDataFromDB(onDataLoaded: Consumer<List<Message>>) {
                assertTrue("getDataFromDB is called in wrong flow", stackTraceIndex == 0)
                stackTraceIndex++
                onDataLoaded.accept(TestData.messagesList)
            }

            override fun saveDataToDB(data: List<Message>) {
                fail("saveDataToDB method is called during failed operation")
            }

            override fun onDestroy() {
                fail("onDestroy method is called during failed operation")
            }
        }, object : INetworkLayer {
            override fun loadDataFromNetwork(
                data: Consumer<List<Message>>,
                onFailed: Consumer<String>
            ) {
                assertTrue("loadDataFromNetwork is called in wrong flow", stackTraceIndex == 1)
                stackTraceIndex++
                onFailed.accept(TestData.errorString)
            }

            override fun onDestroy() {
                fail("onDestroy method is called during failed operation")
            }
        })

        modelLayer.getData(Consumer {
            assertEquals(TestData.messagesList, it)
        }, Consumer {
            assertEquals(it, TestData.errorString)
        })
    }

    @Test
    fun onDestroy() {
        var dbOnDestroyIsCalled = false
        var networkOnDestroyIsCalled = false

        val modelLayer = ModelLayer(object : IDatabaseLayer {
            override fun getDataFromDB(onDataLoaded: Consumer<List<Message>>) {
                fail("Getting DB data in onDestroy method")
            }

            override fun saveDataToDB(data: List<Message>) {
                fail("Saving DB data in onDestroy method")
            }

            override fun onDestroy() {
                dbOnDestroyIsCalled = true
            }
        }, object : INetworkLayer {
            override fun loadDataFromNetwork(
                data: Consumer<List<Message>>,
                onFailed: Consumer<String>
            ) {
                fail("Loading network data in onDestroy method")
            }

            override fun onDestroy() {
                networkOnDestroyIsCalled = true
            }
        })

        modelLayer.onDestroy()

        assertTrue("onDestroy is not called in DB interface", dbOnDestroyIsCalled)
        assertTrue("onDestroy is not called in Network interface", networkOnDestroyIsCalled)
    }
}
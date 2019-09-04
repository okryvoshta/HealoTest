package app.healo.test.network

import java.io.IOException

import fi.iki.elonen.NanoHTTPD

class MockWebServer @Throws(IOException::class)
constructor(port: Int) : NanoHTTPD(port) {

    private var response: String? = null

    init {
        start(SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(
            Response.Status.OK,
            "application/json",
            response
        )
    }

    fun setResponse(response: String) {
        this.response = response
    }
}

package app.healo.test

import app.healo.test.model.db.Message

object TestData {
    val errorString: String = "ErrorString"
    val messagesList: List<Message> = listOf(
        Message(1, 1, "Title1", "Body1"),
        Message(2, 2, "Title2", "Body2"),
        Message(3, 3, "Title3", "Body3")
    )
}
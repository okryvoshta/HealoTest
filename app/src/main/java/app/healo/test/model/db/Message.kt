package app.healo.test.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey var id: Int = 0,
    var userId: Int = 0,
    var title: String? = null,
    var body: String? = null
)

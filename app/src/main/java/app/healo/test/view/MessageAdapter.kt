package app.healo.test.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.healo.test.R
import app.healo.test.model.db.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter(private var mData: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageHolder(textView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val data = mData.get(position)
        with(holder) {
            with(data) {
                mTitleView.text = title
                mBodyView.text = body
                mUserLogo.text = userId.toString()
            }
        }
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTitleView: TextView = itemView.title
        var mBodyView: TextView = itemView.body
        var mUserLogo: TextView = itemView.userLogo
    }

}

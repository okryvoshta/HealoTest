package app.healo.test.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.healo.test.R

class MarginItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = view.resources.getDimensionPixelOffset(R.dimen.CardMargin)
        }
    }
}
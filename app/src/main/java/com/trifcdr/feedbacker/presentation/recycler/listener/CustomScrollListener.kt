package com.trifcdr.lifestylehub.presentation.recycler.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by trifcdr.
 */
abstract class CustomScrollListener(
    private var layoutManager: LinearLayoutManager,
    private var itemCount: Int
): RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val visibleItemCount = layoutManager.childCount - 1
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()


        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + pastVisibleItem >= totalItemCount){
                if (totalItemCount in 0..itemCount) {
                    loadMoreItems()
                }
        }
        }
    }
    abstract fun loadMoreItems()
}
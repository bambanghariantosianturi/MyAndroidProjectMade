package com.example.myandroidproject.kit.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessBottomRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5

    // The current offset index of data you have loaded
    private var currentPage = 1

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true
    private var mLayoutManager: RecyclerView.LayoutManager
    private var mDirection: LoadOnScrollDirection

    constructor(layoutManager: LinearLayoutManager, direction: LoadOnScrollDirection) {
        mLayoutManager = layoutManager
        mDirection = direction
    }

    constructor(layoutManager: GridLayoutManager, direction: LoadOnScrollDirection) {
        mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount()
        mDirection = direction
    }

    constructor(layoutManager: StaggeredGridLayoutManager, direction: LoadOnScrollDirection) {
        mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount()
        mDirection = direction
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var lastVisibleItemPosition = 0
        var firstVisibleItemPosition = 0
        val totalItemCount: Int = mLayoutManager.getItemCount()
        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions: IntArray =
                (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            val firstVisibleItemPositions: IntArray =
                (mLayoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            firstVisibleItemPosition = getFirstVisibleItem(firstVisibleItemPositions)
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            firstVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            firstVisibleItemPosition =
                (mLayoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        }
        when (mDirection) {
            LoadOnScrollDirection.BOTTOM -> {
                // If the total item count is zero and the previous isn't, assume the
                // list is invalidated and should be reset back to initial state
                if (totalItemCount < previousTotalItemCount) {
                    currentPage = startingPageIndex
                    previousTotalItemCount = totalItemCount
                    if (totalItemCount == 0) {
                        loading = true
                    }
                }
                // If it’s still loading, we check to see if the dataset count has
                // changed, if so we conclude it has finished loading and update the current page
                // number and total item count.
                if (loading && totalItemCount > previousTotalItemCount) {
                    loading = false
                    previousTotalItemCount = totalItemCount
                }

                // If it isn’t currently loading, we check to see if we have breached
                // the visibleThreshold and need to reload more data.
                // If we do need to reload some more data, we execute onLoadMore to fetch the data.
                // threshold should reflect how many total columns there are too
                if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                    currentPage++
                    onLoadMore(currentPage, totalItemCount)
                    loading = true
                }
            }
            LoadOnScrollDirection.TOP -> {
                // If the total item count is zero and the previous isn't, assume the
                // list is invalidated and should be reset back to initial state
                if (totalItemCount < previousTotalItemCount) {
                    currentPage = startingPageIndex
                    previousTotalItemCount = totalItemCount
                    if (totalItemCount == 0) {
                        loading = true
                    }
                }
                // If it’s still loading, we check to see if the dataset count has
                // changed, if so we conclude it has finished loading and update the current page
                // number and total item count.
                if (loading && totalItemCount > previousTotalItemCount) {
                    loading = false
                    previousTotalItemCount = totalItemCount
                }

                // If it isn’t currently loading, we check to see if we have breached
                // the visibleThreshold and need to reload more data.
                // If we do need to reload some more data, we execute onLoadMore to fetch the data.
                // threshold should reflect how many total columns there are too
                if (!loading && firstVisibleItemPosition < visibleThreshold) {
                    currentPage++
                    onLoadMore(currentPage, totalItemCount)
                    loading = true
                }
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    private fun getFirstVisibleItem(firstVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in firstVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = firstVisibleItemPositions[i]
            } else if (firstVisibleItemPositions[i] > maxSize) {
                maxSize = firstVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
    enum class LoadOnScrollDirection {
        TOP, BOTTOM
    }

    companion object {
        // Sets the starting page index
        private const val startingPageIndex = 1
    }
}
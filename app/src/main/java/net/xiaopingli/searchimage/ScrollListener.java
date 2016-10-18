package net.xiaopingli.searchimage;

import android.widget.AbsListView;

/**
 * Created by xiaopingli on 10/17/16.
 */

public abstract class ScrollListener implements AbsListView.OnScrollListener {
    private int visibleThreshold = 5;		// Min number of items below current scroll position before loading more
    private int currentPage = 0;		// Current offset index of data already loaded
    private int previousTotalItemCount = 0;		// Total number of items in the dataset following last load
    private int startingPageIndex = 0;		// Sets starting page index
    private boolean isLoading = true;		// True if waiting for the last dataset to load

    public ScrollListener() {
    }

    public ScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public ScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        startingPageIndex = startPage;
        currentPage = startPage;
    }

    /*
     * Callback method for user scrolling.
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If condition true, assumes list has been invalidated and should be reset to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex;
            previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) isLoading = true;
        }
        // Condition tests for cessation of loading, which is assumed if the inequality holds
        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }
        // Condition tests whether more data needs to be loaded. If so, calls onLoadMore(int, int) to
        // fetch more data.
        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }

    /*
     * Defines process for loading more data, based on page and any applicable API protocol
     */
    public abstract void onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }

}

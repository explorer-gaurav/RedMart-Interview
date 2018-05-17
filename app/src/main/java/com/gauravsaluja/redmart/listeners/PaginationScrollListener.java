package com.gauravsaluja.redmart.listeners;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 * <p>
 * Pagination listener to intercept next page load and apply logic for next page load
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private GridLayoutManager layoutManager;

    public PaginationScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        // logic for next page load
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {

                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    protected abstract boolean isLastPage();

    protected abstract boolean isLoading();
}
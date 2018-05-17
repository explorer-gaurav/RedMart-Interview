package com.gauravsaluja.redmart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.adapters.ListingAdapter;
import com.gauravsaluja.redmart.base.BaseFragment;
import com.gauravsaluja.redmart.decorators.ItemOffsetDecoration;
import com.gauravsaluja.redmart.listeners.PaginationScrollListener;
import com.gauravsaluja.redmart.presenters.ListingPresenter;
import com.gauravsaluja.redmart.presenters.impl.ListingPresenterImpl;
import com.gauravsaluja.redmart.utils.Connectivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Fragment for product listing
 */

public class ListingFragment extends BaseFragment implements ListingPresenter.View {

    @BindView(R.id.product_listing)
    public RecyclerView productListingRecyclerView;
    @BindView(R.id.progress_load)
    public ProgressBar progressLoad;
    @BindView(R.id.no_results_container)
    public ConstraintLayout noResultsContainer;
    @BindView(R.id.action_retry)
    public TextView actionRetry;
    @BindView(R.id.retry_text)
    public TextView retryText;

    private Unbinder unbinder;
    private GridLayoutManager gridLayoutManager;
    private ItemOffsetDecoration itemDecoration;
    private int GRID_SPAN_COUNT = 3;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = -1;
    private int currentPage = PAGE_START;

    @Inject
    public ListingPresenterImpl listingPresenter;
    @Inject
    public ListingAdapter listingAdapter;

    // create new instance of ListingFragment
    public static ListingFragment newInstance() {
        ListingFragment fragment = new ListingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        listingPresenter.setView(this);

        initialize();
    }

    // initialize the fragment and initial setup
    private void initialize() {

        // set grid layout manager with grid span count
        gridLayoutManager = new GridLayoutManager(getActivity(), GRID_SPAN_COUNT);
        itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.value_4dp);

        // setup recycler view
        productListingRecyclerView.setAdapter(listingAdapter);
        productListingRecyclerView.addItemDecoration(itemDecoration);
        productListingRecyclerView.setLayoutManager(gridLayoutManager);

        // add pagination listener
        productListingRecyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                loadMoreOnScroll();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // send product listing network call
        loadProductList();
    }

    @OnClick(R.id.action_retry)
    public void loadProductList() {

        // if internet connectivity is available, proceed with network call
        // else show no results screen with option to retry
        if (Connectivity.isConnected(getActivity())) {
            try {
                listingPresenter.load(currentPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            productListingRecyclerView.setVisibility(View.GONE);
            progressLoad.setVisibility(View.GONE);

            noResultsContainer.setVisibility(View.VISIBLE);
            retryText.setText(R.string.error_no_internet);
        }
    }

    // action --> load more results
    private void loadMoreOnScroll() {
        isLoading = true;
        currentPage += 1;

        loadProductList();
    }

    @Override
    public void onProductListingLoading() {
        if (listingAdapter.isEmpty()) {
            productListingRecyclerView.setVisibility(View.GONE);
            progressLoad.setVisibility(View.VISIBLE);
        }

        noResultsContainer.setVisibility(View.GONE);
    }

    @Override
    public void onProductListingLoaded(ProductListingResponse listingResponse) {

        productListingRecyclerView.setVisibility(View.VISIBLE);
        progressLoad.setVisibility(View.GONE);
        noResultsContainer.setVisibility(View.GONE);

        // calculate total pages based on api response
        if (listingResponse.getPageSize() != 0) {
            int pages = (listingResponse.getTotal() / listingResponse.getPageSize());
            if (Math.ceil(pages) == pages && Math.floor(pages) == pages) {
                TOTAL_PAGES = (listingResponse.getTotal() / listingResponse.getPageSize());
            } else {
                TOTAL_PAGES = (listingResponse.getTotal() / listingResponse.getPageSize()) + 1;
            }
        }

        // if not on first page, then remove loading footer first and mark isLoading to be false
        if (currentPage > 0) {
            listingAdapter.removeLoadingFooter();
            isLoading = false;
        }

        // add all the results to current adapter
        listingAdapter.addAll(listingResponse.getProducts());

        // if current page exceeds total pages then we have reached last page
        if (currentPage <= TOTAL_PAGES) listingAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void onProductListingFailed() {
        noResultsContainer.setVisibility(View.VISIBLE);
        productListingRecyclerView.setVisibility(View.GONE);
        progressLoad.setVisibility(View.GONE);

        retryText.setText(R.string.no_results_product_listing);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();

        listingPresenter.destroy();
        listingPresenter = null;
        listingAdapter = null;
    }
}
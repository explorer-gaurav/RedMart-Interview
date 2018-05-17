package com.gauravsaluja.redmart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.viewholder.ListingItemViewHolder;
import com.gauravsaluja.redmart.viewholder.ListingLoadMoreViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Product listing adapter
 */

public class ListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Product> productList;
    private boolean isLoadingAdded = false;

    @Inject
    public ListingAdapter() {
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_product_listing, parent, false);
                viewHolder = new ListingItemViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_load_more, parent, false);
                viewHolder = new ListingLoadMoreViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Product product = productList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ListingItemViewHolder viewHolder = (ListingItemViewHolder) holder;
                viewHolder.bind(product);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == productList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    // add a new product to list and notify change
    public void add(Product product) {
        productList.add(product);
        notifyItemInserted(productList.size() - 1);
    }

    // add the passed list of products
    public void addAll(List<Product> productList) {
        for (Product product : productList) {
            add(product);
        }
    }

    // remove a product
    public void remove(Product r) {
        int position = productList.indexOf(r);
        if (position > -1) {
            productList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // check if list is empty
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    // add loading footer
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Product());
    }

    // remove loading footer
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        if (productList.size() >= 1) {
            int position = productList.size() - 1;
            Product result = getItem(position);

            if (result != null) {
                productList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    // get product item
    public Product getItem(int position) {
        return productList.get(position);
    }
}
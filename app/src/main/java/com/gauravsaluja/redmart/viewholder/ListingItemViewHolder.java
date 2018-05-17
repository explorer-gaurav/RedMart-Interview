package com.gauravsaluja.redmart.viewholder;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.activities.DetailsActivity;
import com.gauravsaluja.redmart.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 * <p>
 * View holder for product item
 */

public class ListingItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_info_layout)
    public RelativeLayout productInfoLayout;

    @BindView(R.id.product_image)
    public ImageView productImage;

    @BindView(R.id.product_name)
    public TextView productName;

    @BindView(R.id.product_quantity)
    public TextView productQuantity;

    @BindView(R.id.product_promo_price)
    public TextView productPromoPrice;

    @BindView(R.id.product_price)
    public TextView productPrice;

    private Product itemProduct;

    public ListingItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    // bind view holder to product
    public void bind(Product product) {
        itemProduct = product;

        // load product image
        Picasso.with(productImage.getContext())
                .load(Constants.BASE_IMAGE_URL + product.getImg().getName())
                .into(productImage);

        // set name
        productName.setText(product.getTitle());

        // check if product quantity is available or not
        if (!TextUtils.isEmpty(product.getMeasure().getWtOrVol())) {
            productQuantity.setText(product.getMeasure().getWtOrVol());
            productQuantity.setVisibility(View.VISIBLE);
        } else {
            productQuantity.setVisibility(View.GONE);
        }

        // check if promo price is available or not
        // if available, then promo price and price needs to be displayed
        // else only price
        if (product.getPricing().getPromoPrice() == null || product.getPricing().getPromoPrice().intValue() == 0) {
            productPromoPrice.setVisibility(View.GONE);
            productPrice.setTextSize(12);
            productPrice.setPaintFlags(0);
        } else {
            productPromoPrice.setText("$" + product.getPricing().getPromoPrice());
            productPromoPrice.setVisibility(View.VISIBLE);

            productPrice.setTextSize(10);
            productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        productPrice.setText("$" + product.getPricing().getPrice());
    }

    // click listener for product item
    @OnClick(R.id.product_info_layout)
    public void onProductItemClick() {
        DetailsActivity.startActivity(productInfoLayout.getContext(), itemProduct);
    }
}
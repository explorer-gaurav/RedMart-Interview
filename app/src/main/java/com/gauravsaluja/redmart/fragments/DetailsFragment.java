package com.gauravsaluja.redmart.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gauravsaluja.domain.model.Primary;
import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.model.Secondary;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.adapters.ProductImageAdapter;
import com.gauravsaluja.redmart.base.BaseFragment;
import com.gauravsaluja.redmart.customviews.CirclePageIndicator;
import com.gauravsaluja.redmart.presenters.DetailsPresenter;
import com.gauravsaluja.redmart.presenters.impl.DetailsPresenterImpl;
import com.gauravsaluja.redmart.utils.Connectivity;
import com.gauravsaluja.redmart.utils.Constants;
import com.gauravsaluja.redmart.utils.Helpers;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 * <p>
 * Fragment for product details
 */

public class DetailsFragment extends BaseFragment implements DetailsPresenter.View {

    private static final String KEY_PRODUCT_ID = "product_id";
    private int productId = 0;
    private ProductImageAdapter productImageAdapter;

    @BindView(R.id.product_detail_parent_layout)
    LinearLayout detailParentLayout;
    @BindView(R.id.progress_load)
    ProgressBar progressLoad;
    @BindView(R.id.images_pager)
    ViewPager imagesViewPager;
    @BindView(R.id.images_indicator)
    CirclePageIndicator imagesIndicator;
    @BindView(R.id.info_product_name)
    TextView productName;
    @BindView(R.id.info_product_quantity)
    TextView productQuantity;
    @BindView(R.id.info_product_promo_price)
    TextView productPromoPrice;
    @BindView(R.id.info_product_price)
    TextView productPrice;
    @BindView(R.id.container_item_description)
    LinearLayout containerItemDescription;
    @BindView(R.id.no_results_container)
    public ConstraintLayout noResultsContainer;
    @BindView(R.id.action_retry)
    public TextView actionRetry;
    @BindView(R.id.retry_text)
    public TextView retryText;

    private Unbinder unbinder;

    @Inject
    public DetailsPresenterImpl detailsPresenter;

    // create new instance of DetailsFragment
    public static DetailsFragment newInstance(int productId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PRODUCT_ID, productId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        setRetainInstance(true);

        // get product id from bundle
        Bundle bundle = getArguments();
        productId = bundle.containsKey(KEY_PRODUCT_ID) ? bundle.getInt(KEY_PRODUCT_ID) : 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        detailsPresenter.setView(this);

        initialize();
    }

    // initialize the fragment and initial setup
    private void initialize() {

        // send product detail network call
        loadProductDetails();
    }

    @OnClick(R.id.action_retry)
    public void loadProductDetails() {

        // if internet connectivity is available, proceed with network call
        // else show no results screen with option to retry
        if (Connectivity.isConnected(getActivity())) {
            try {
                detailsPresenter.load(productId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            detailParentLayout.setVisibility(View.GONE);
            progressLoad.setVisibility(View.GONE);

            noResultsContainer.setVisibility(View.VISIBLE);
            retryText.setText(R.string.error_no_internet);
        }
    }

    @Override
    public void onProductDetailLoading() {
        progressLoad.setVisibility(View.VISIBLE);
        detailParentLayout.setVisibility(View.GONE);
        noResultsContainer.setVisibility(View.GONE);
    }

    @Override
    public void onProductDetailLoaded(ProductDetailResponse detailResponse) {
        detailParentLayout.setVisibility(View.VISIBLE);
        progressLoad.setVisibility(View.GONE);
        noResultsContainer.setVisibility(View.GONE);

        // get product object from response and populate the details fragment
        Product product = detailResponse.getProduct();
        productName.setText(product.getTitle());

        // check if product quantity is available or not
        if (!TextUtils.isEmpty(product.getMeasure().getWtOrVol())) {
            productQuantity.setText(product.getMeasure().getWtOrVol());
            productQuantity.setVisibility(View.VISIBLE);
        } else {
            productQuantity.setVisibility(View.GONE);
        }

        // set pricing fields logic
        setProductPricing(product);

        // setup image view pager
        setupPager(product);

        // add description fields dynamically based on api response
        addDescriptionFields(product);
    }

    // set pricing fields logic
    private void setProductPricing(Product product) {
        if (product.getPricing().getPromoPrice() == null || product.getPricing().getPromoPrice().intValue() == 0) {
            productPromoPrice.setVisibility(View.GONE);
            productPrice.setTextSize(18);
            productPrice.setPaintFlags(0);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) productPrice.getContext().getResources().getDimension(R.dimen.value_20dp),
                    (int) productPrice.getContext().getResources().getDimension(R.dimen.value_4dp),
                    (int) productPrice.getContext().getResources().getDimension(R.dimen.value_4dp), 0);
            params.addRule(RelativeLayout.BELOW, R.id.separator_quantity_price);
            productPrice.setLayoutParams(params);
            productPrice.setTypeface(Helpers.getNunitoTypeFaceBold(productPrice.getContext()));
        } else {
            productPromoPrice.setText("$" + product.getPricing().getPromoPrice());
            productPromoPrice.setVisibility(View.VISIBLE);

            productPrice.setTextSize(12);
            productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (int) productPrice.getContext().getResources().getDimension(R.dimen.value_4dp), 0, 0);
            params.addRule(RelativeLayout.BELOW, R.id.separator_quantity_price);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.info_product_promo_price);
            params.addRule(RelativeLayout.ALIGN_BASELINE, R.id.info_product_promo_price);
            productPrice.setLayoutParams(params);
            productPrice.setTypeface(Helpers.getNunitoTypeFaceLight(productPrice.getContext()));
        }

        productPrice.setText("$" + product.getPricing().getPrice());
    }

    // setup image view pager
    private void setupPager(Product product) {
        productImageAdapter = new ProductImageAdapter(getFragmentManager(), product.getImages());
        imagesViewPager.setOffscreenPageLimit(productImageAdapter.getCount());
        imagesViewPager.setAdapter(productImageAdapter);
        imagesIndicator.setViewPager(imagesViewPager);
        imagesIndicator.setOrientation(LinearLayout.HORIZONTAL);
    }

    // add description fields dynamically based on api response
    private void addDescriptionFields(Product product) {
        int countPrimaryFields = 0;
        int countSecondaryFields = 0;

        if (product.getDescriptionFields().getPrimary() != null)
            countPrimaryFields += product.getDescriptionFields().getPrimary().size();
        if (product.getDescriptionFields().getSecondary() != null)
            countSecondaryFields += product.getDescriptionFields().getSecondary().size();

        final Context context = containerItemDescription.getContext();
        containerItemDescription.removeAllViews();

        LinearLayout parentLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parentLayout.setLayoutParams(params);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.removeAllViews();

        // for all primary fields in api response, add them to description layout
        for (int i = 0; i < countPrimaryFields; i++) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_product_description, null, false);
            TextView title = itemView.findViewById(R.id.description_title);
            TextView content = itemView.findViewById(R.id.description_content);

            Primary primary = product.getDescriptionFields().getPrimary().get(i);
            title.setText(primary.getName());
            content.setText(primary.getContent());

            // add resizable option to content field
            Helpers.makeTextViewResizable(getActivity(), content, Constants.MAX_LINES, Constants.READ_MORE_STRING, true);

            parentLayout.addView(itemView);
        }

        // for all secondary fields in api response, add them to description layout
        for (int i = 0; i < countSecondaryFields; i++) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_product_description, null, false);
            TextView title = itemView.findViewById(R.id.description_title);
            TextView content = itemView.findViewById(R.id.description_content);

            Secondary secondary = product.getDescriptionFields().getSecondary().get(i);
            title.setText(secondary.getName());
            content.setText(secondary.getContent());

            // add resizable option to content field
            Helpers.makeTextViewResizable(getActivity(), content, Constants.MAX_LINES, Constants.READ_MORE_STRING, true);

            parentLayout.addView(itemView);
        }

        containerItemDescription.addView(parentLayout);
    }

    @Override
    public void onProductDetailFailed() {
        noResultsContainer.setVisibility(View.VISIBLE);
        detailParentLayout.setVisibility(View.GONE);
        progressLoad.setVisibility(View.GONE);

        retryText.setText(R.string.no_results_product_details);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();

        detailsPresenter.destroy();
        detailsPresenter = null;
    }
}
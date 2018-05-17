package com.gauravsaluja.redmart.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.base.BaseActivity;
import com.gauravsaluja.redmart.fragments.DetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Product Detail Activity
 */

public class DetailsActivity extends BaseActivity {

    private static final String KEY_PRODUCT = "product";

    @BindView(R.id.toolbar_details)
    public Toolbar toolbar;
    @BindView(R.id.details_title)
    public TextView title;

    private Product product;

    // start DetailsActivity
    public static void startActivity(Context context, Product product) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_PRODUCT, product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        ButterKnife.bind(this);

        // retrieve product object from intent
        Intent intent = getIntent();
        product = intent.hasExtra(KEY_PRODUCT) ? (Product) intent.getSerializableExtra(KEY_PRODUCT) : null;

        // setup toolbar and title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(product == null || TextUtils.isEmpty(product.getTitle())) {
            title.setText(R.string.detail_activity_title);
        } else {
            title.setText(product.getTitle());
        }

        initFragment();
    }

    // initialize fragment
    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(product.getId()))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
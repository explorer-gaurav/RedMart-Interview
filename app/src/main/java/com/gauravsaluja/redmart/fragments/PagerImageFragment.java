package com.gauravsaluja.redmart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gauravsaluja.domain.model.Image;
import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.base.BaseFragment;
import com.gauravsaluja.redmart.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 * <p>
 * Image item fragment for viewpager
 */

public class PagerImageFragment extends BaseFragment {

    private Unbinder unbinder;
    private static String KEY_IMAGE_DATA = "image";

    @BindView(R.id.item_cover)
    public ImageView itemCover;

    private Image image;

    // create new instance of PagerImageFragment
    public static PagerImageFragment newInstance(Image image) {
        PagerImageFragment fragment = new PagerImageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_IMAGE_DATA, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve image object from bundle
        Bundle b = getArguments();
        if (b != null && b.containsKey(KEY_IMAGE_DATA)) {
            image = (Image) b.getSerializable(KEY_IMAGE_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        // load image in itemCover
        Picasso.with(getContext())
                .load(Constants.BASE_IMAGE_URL + image.getName())
                .error(R.color.placeholder)
                .into(itemCover);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
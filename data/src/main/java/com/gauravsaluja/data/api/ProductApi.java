package com.gauravsaluja.data.api;

import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.model.ProductListingResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Interface for defining the API calls available with params and request types
 */

public interface ProductApi {

    @GET("search")
    Observable<ProductListingResponse> getProductsListing(@QueryMap Map<String, Object> queryParams);

    @GET("products/{product_id}")
    Observable<ProductDetailResponse> getProductDetails(@Path("product_id") int id);
}
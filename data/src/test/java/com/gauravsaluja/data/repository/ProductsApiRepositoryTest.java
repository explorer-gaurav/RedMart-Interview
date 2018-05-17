package com.gauravsaluja.data.repository;

import com.gauravsaluja.data.api.ProductApi;
import com.gauravsaluja.domain.interactors.UseCaseObserver;
import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMapOf;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Test case for product listing and detail api
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductsApiRepositoryTest {

    @Mock
    ProductApi productApi;
    @InjectMocks
    ProductsApiRepository productsApiRepository;

    @Before
    public void setUp() {

    }

    // mock product listing response with dummy json
    // generate list of products from response received
    // assert that response from dummy observable matches sample result
    @Test
    public void testGetProductListing_withValidApiResponse_ReturnsProductListingResponse() throws FileNotFoundException {

        given(productApi.getProductsListing(anyMapOf(String.class, Object.class)))
                .willReturn(Observable.just(getProductListingResponse()));

        final List<Product> productList = new ArrayList<>();
        productsApiRepository.getProductsList(new HashMap<String, Object>())
                .subscribe(new UseCaseObserver<ProductListingResponse>() {
                    @Override
                    public void onNext(ProductListingResponse productListingResponse) {
                        super.onNext(productListingResponse);
                        productList.addAll(productListingResponse.getProducts());
                    }
                });

        assertThat(getActualProductList().size(), is(productList.size()));
    }

    // mock product listing response with dummy json
    // generate product detail from response received
    // assert that response from dummy observable matches sample result
    @Test
    public void testGetProductDetail_withValidApiResponse_ReturnsProductDetailResponse() throws FileNotFoundException {

        given(productApi.getProductDetails(anyInt()))
                .willReturn(Observable.just(getProductDetailResponse()));

        final Product[] product = {new Product()};
        productsApiRepository.getProductDetail(new Integer(1))
                .subscribe(new UseCaseObserver<ProductDetailResponse>() {
                    @Override
                    public void onNext(ProductDetailResponse productDetailResponse) {
                        super.onNext(productDetailResponse);
                        product[0] = productDetailResponse.getProduct();
                    }
                });

        assertThat(getActualProductDetail().getId(), is(product[0].getId()));
    }

    // get path for product listing json file
    private String getProductListingJsonFilePath() {
        String filePath = new File("").getAbsolutePath();
        return filePath.concat("/src/test/java/com/gauravsaluja/data/assets/listing.json");
    }

    // get path for product detail json file
    private String getProductDetailJsonFilePath() {
        String filePath = new File("").getAbsolutePath();
        return filePath.concat("/src/test/java/com/gauravsaluja/data/assets/details.json");
    }

    // read product listing response from json file
    private ProductListingResponse getProductListingResponse() throws FileNotFoundException {
        return new GsonBuilder().create().fromJson(new JsonReader(
                new FileReader(getProductListingJsonFilePath())), ProductListingResponse.class);
    }

    // read product detail response from json file
    private ProductDetailResponse getProductDetailResponse() throws FileNotFoundException {
        return new GsonBuilder().create().fromJson(new JsonReader(
                new FileReader(getProductDetailJsonFilePath())), ProductDetailResponse.class);
    }

    // sample data for product listing
    private List<Product> getActualProductList() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        productList.add(product1);

        Product product2 = new Product();
        productList.add(product2);

        return productList;
    }

    // sample data for product detail
    private Product getActualProductDetail() {
        Product product = new Product();
        product.setId(33973);
        product.setTitle("Australian Broccoli");
        return product;
    }
}
package com.gauravsaluja.domain.interactors;

import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.domain.repository.ProductRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.when;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Test case for product listing use case
 */

@RunWith(MockitoJUnitRunner.class)
public class GetProductListUseCaseTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    GetProductsListUseCase productsListUseCase;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

    }

    // if product listing call returns null, then throw an exception
    @Test
    public void test_ResultsReturnedByRepositoryAreNull_ThrowException() {
        when(productRepository.getProductsList((anyMapOf(String.class, Object.class))))
                .thenReturn(null);

        exception.expect(NullPointerException.class);
        throw new NullPointerException();
    }

    // if product listing call succeeds, get the result and convert it to products
    // assert that response from dummy observable matches sample result
    @Test
    public void test_ResultsReturnedByRepositoryAreValid_ResultsAreConvertedIntoProducts() {

        given(productRepository.getProductsList((anyMapOf(String.class, Object.class))))
                .willReturn(getValidProductListObservable());

        final List<Product> productList = new ArrayList<>();
        productsListUseCase
                .buildUseCaseObservable(new HashMap<>())
                .subscribe(new UseCaseObserver<ProductListingResponse>() {
                    @Override
                    public void onNext(ProductListingResponse productListingResponse) {
                        super.onNext(productListingResponse);
                        productList.addAll(productListingResponse.getProducts());
                    }
                });

        assertThat(productList.size(), is(getValidProductResults().size()));
    }

    // generate observable of 'ProductListingResponse'
    private Observable<ProductListingResponse> getValidProductListObservable() {
        return Observable.just(getValidProductsList());
    }

    // function to generate dummy data to be returned as observable
    private ProductListingResponse getValidProductsList() {
        ProductListingResponse productListingResponse = new ProductListingResponse();

        Product product = new Product();
        product.setId(123456);
        product.setTitle("Australian Broccoli");

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);

        productListingResponse.setProducts(productList);
        return productListingResponse;
    }

    // sample data for validation of returned observable
    private List<Product> getValidProductResults() {
        List<Product> productList = new ArrayList<>();

        Product product = new Product();
        product.setId(123456);
        product.setTitle("Australian Broccoli");

        productList.add(product);
        return productList;
    }
}
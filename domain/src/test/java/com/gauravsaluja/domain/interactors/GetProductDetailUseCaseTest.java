package com.gauravsaluja.domain.interactors;

import com.gauravsaluja.domain.model.Product;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.repository.ProductRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 * <p>
 * Test case for product detail use case
 */

@RunWith(MockitoJUnitRunner.class)
public class GetProductDetailUseCaseTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    GetProductDetailUseCase productDetailUseCase;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

    }

    // if product detail call returns null, then throw an exception
    @Test
    public void test_ResultsReturnedByRepositoryAreNull_ThrowException() {
        when(productRepository.getProductDetail((anyInt())))
                .thenReturn(null);

        exception.expect(NullPointerException.class);
        throw new NullPointerException();
    }

    // if product detail call succeeds, get the result and validate it be of 'Product' type
    // assert that response from dummy observable matches sample result
    @Test
    public void test_ResultsReturnedByRepositoryAreValid_ResultsIsAProduct() {

        given(productRepository.getProductDetail((anyInt())))
                .willReturn(getValidProductDetailObservable());

        final Product[] product = {new Product()};
        productDetailUseCase
                .buildUseCaseObservable(new Integer(1))
                .subscribe(new UseCaseObserver<ProductDetailResponse>() {
                    @Override
                    public void onNext(ProductDetailResponse productDetailResponse) {
                        super.onNext(productDetailResponse);
                        product[0] = productDetailResponse.getProduct();
                    }
                });

        assertThat(product[0].getId(), is(getValidProductResult().getId()));
    }

    // generate observable of 'ProductDetailResponse'
    private Observable<ProductDetailResponse> getValidProductDetailObservable() {
        return Observable.just(getValidProductDetail());
    }

    // function to generate dummy data to be returned as observable
    private ProductDetailResponse getValidProductDetail() {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();

        Product product = new Product();
        product.setId(123456);
        product.setTitle("Australian Broccoli");

        productDetailResponse.setProduct(product);
        return productDetailResponse;
    }

    // sample data for validation of returned observable
    private Product getValidProductResult() {
        Product product = new Product();
        product.setId(123456);
        product.setTitle("Australian Broccoli");
        return product;
    }
}
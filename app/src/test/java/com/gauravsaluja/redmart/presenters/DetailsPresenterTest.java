package com.gauravsaluja.redmart.presenters;

import com.gauravsaluja.domain.interactors.GetProductDetailUseCase;
import com.gauravsaluja.domain.interactors.UseCaseObserver;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.redmart.presenters.impl.DetailsPresenterImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 *
 * Presenter test for product detail
 */

@RunWith(MockitoJUnitRunner.class)
public class DetailsPresenterTest {

    @Mock
    GetProductDetailUseCase getProductDetailUseCase;
    @Mock
    DetailsPresenter.View presenterView;
    @InjectMocks
    DetailsPresenterImpl detailsPresenter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

    }

    // if view not set, throw exception
    @Test
    public void testLoad_ViewNotSet_ExceptionIsThrown() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("setView() not called Before calling load()");
        detailsPresenter.load(0);
    }

    // if view not set, then no interactions happen if we call the load functionality
    @Test
    public void testLoad_ViewNotSet_ViewAndUseCaseHasZeroInteractions() {

        try {
            detailsPresenter.load(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyZeroInteractions(presenterView);
        verifyZeroInteractions(getProductDetailUseCase);
    }

    // if view is set and we get successful response, then there is exactly
    // one interaction for onProductDetailLoading(), dispose() and onProductDetailLoaded() each
    // and zero interactions for onProductDetailFailed()
    @Test
    public void testLoad_ViewSetAndGotProductDetail_ViewAndUseCaseHasPositiveInteractions() throws Exception {

        detailsPresenter.setView(presenterView);
        int productId = 0;

        final ProductDetailResponse productDetailResponse = new ProductDetailResponse();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DisposableObserver<ProductDetailResponse>) invocation.getArguments()[0]).onNext(productDetailResponse);
                return null;
            }
        }).when(getProductDetailUseCase).execute(
                any(UseCaseObserver.class),
                anyInt());


        detailsPresenter.load(productId);

        verify(presenterView, times(1)).onProductDetailLoading();
        verify(getProductDetailUseCase, times(1)).dispose();
        verify(presenterView, times(1)).onProductDetailLoaded(productDetailResponse);
        verify(presenterView, times(0)).onProductDetailFailed();
    }

    // if view is set and we get failure response, then there is exactly
    // zero interactions for onProductDetailFailed()
    // and one interaction for onProductDetailLoading(), dispose() and onProductDetailLoaded() each
    @Test
    public void testLoad_ViewSetAndProductDetailFailed_ViewHasNegativeInteractions() throws Exception {

        detailsPresenter.setView(presenterView);
        int productId = 0;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DisposableObserver<ProductDetailResponse>) invocation.getArguments()[0]).onError(new Throwable());
                return null;
            }
        }).when(getProductDetailUseCase).execute(
                any(UseCaseObserver.class),
                anyInt());


        detailsPresenter.load(productId);

        verify(presenterView, times(1)).onProductDetailLoading();
        verify(getProductDetailUseCase, times(1)).dispose();
        verify(presenterView, times(0)).onProductDetailLoaded(any(ProductDetailResponse.class));
        verify(presenterView, times(1)).onProductDetailFailed();
    }
}
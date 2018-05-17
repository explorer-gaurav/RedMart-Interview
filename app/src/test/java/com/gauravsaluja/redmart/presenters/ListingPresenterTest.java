package com.gauravsaluja.redmart.presenters;

import com.gauravsaluja.domain.interactors.GetProductsListUseCase;
import com.gauravsaluja.domain.interactors.UseCaseObserver;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.redmart.presenters.impl.ListingPresenterImpl;

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
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 *
 * Presenter test for product listing
 */

@RunWith(MockitoJUnitRunner.class)
public class ListingPresenterTest {

    @Mock
    GetProductsListUseCase getProductsListUseCase;
    @Mock
    ListingPresenter.View presenterView;
    @InjectMocks
    ListingPresenterImpl listingPresenter;

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
        listingPresenter.load(0);
    }

    // if view not set, then no interactions happen if we call the load functionality
    @Test
    public void testLoad_ViewNotSet_ViewAndUseCaseHasZeroInteractions(){

        try {
            listingPresenter.load(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyZeroInteractions(presenterView);
        verifyZeroInteractions(getProductsListUseCase);
    }

    // if view is set and we get successful response, then there is exactly
    // one interaction for onProductListingLoading(), dispose() and onProductListingLoaded() each
    // and zero interactions for onProductListingFailed()
    @Test
    public void testLoad_ViewSetAndGotProductListing_ViewAndUseCaseHasPositiveInteractions() throws Exception {

        listingPresenter.setView(presenterView);
        int pageNo = 0;

        final ProductListingResponse productListingResponse = new ProductListingResponse();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DisposableObserver<ProductListingResponse>) invocation.getArguments()[0]).onNext(productListingResponse);
                return null;
            }
        }).when(getProductsListUseCase).execute(
                any(UseCaseObserver.class),
                anyMapOf(String.class, Object.class));

        listingPresenter.load(pageNo);

        verify(presenterView, times(1)).onProductListingLoading();
        verify(getProductsListUseCase, times(1)).dispose();
        verify(presenterView, times(1)).onProductListingLoaded(productListingResponse);
        verify(presenterView, times(0)).onProductListingFailed();
    }

    // if view is set and we get failure response, then there is exactly
    // one interaction for onProductListingFailed()
    // and zero interactions for onProductListingLoading(), dispose() and onProductListingLoaded() each
    @Test
    public void testLoad_ViewSetAndProductListingFailed_ViewHasNegativeInteractions() throws Exception {

        listingPresenter.setView(presenterView);
        int pageNo = 0;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DisposableObserver<ProductListingResponse>) invocation.getArguments()[0]).onError(new Throwable());
                return null;
            }
        }).when(getProductsListUseCase).execute(
                any(UseCaseObserver.class),
                anyMapOf(String.class, Object.class));


        listingPresenter.load(pageNo);

        verify(presenterView, times(1)).onProductListingLoading();
        verify(getProductsListUseCase, times(1)).dispose();
        verify(presenterView, times(0)).onProductListingLoaded(any(ProductListingResponse.class));
        verify(presenterView, times(1)).onProductListingFailed();
    }
}
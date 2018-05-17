package com.gauravsaluja.domain.interactors;

import com.gauravsaluja.domain.executor.Executor;
import com.gauravsaluja.domain.executor.PostExecutionThread;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.domain.repository.ProductRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Use case for getting product list
 */

public class GetProductsListUseCase extends UseCase<ProductListingResponse, Map<String, Object>> {

    private ProductRepository productRepository;

    @Inject
    public GetProductsListUseCase(Executor threadExecutor, PostExecutionThread postExecutionThread,
                                  ProductRepository repository) {

        super(threadExecutor, postExecutionThread);
        this.productRepository = repository;
    }

    // build observable for getting product list response
    @Override
    public Observable<ProductListingResponse> buildUseCaseObservable(Map<String, Object> queryParams) {
        return productRepository.getProductsList(queryParams);
    }
}
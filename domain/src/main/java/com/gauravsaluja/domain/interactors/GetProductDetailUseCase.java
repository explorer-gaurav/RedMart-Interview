package com.gauravsaluja.domain.interactors;

import com.gauravsaluja.domain.executor.Executor;
import com.gauravsaluja.domain.executor.PostExecutionThread;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.repository.ProductRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Use case for getting product detail
 */

public class GetProductDetailUseCase extends UseCase<ProductDetailResponse, Integer> {

    private ProductRepository productRepository;

    @Inject
    public GetProductDetailUseCase(Executor threadExecutor, PostExecutionThread postExecutionThread,
                                   ProductRepository repository) {

        super(threadExecutor, postExecutionThread);
        this.productRepository = repository;
    }

    // build observable for getting product detail response
    @Override
    public Observable<ProductDetailResponse> buildUseCaseObservable(Integer productId) {
        return productRepository.getProductDetail(productId);
    }
}
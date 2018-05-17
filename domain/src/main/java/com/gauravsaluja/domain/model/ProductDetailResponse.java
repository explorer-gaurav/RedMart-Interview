
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class ProductDetailResponse implements Serializable {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    private final static long serialVersionUID = -8000434506431799065L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
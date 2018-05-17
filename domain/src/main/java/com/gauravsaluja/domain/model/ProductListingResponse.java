
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class ProductListingResponse implements Serializable {

    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("on_sale_count")
    @Expose
    private Integer onSaleCount;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    private final static long serialVersionUID = 7172444356399061447L;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getOnSaleCount() {
        return onSaleCount;
    }

    public void setOnSaleCount(Integer onSaleCount) {
        this.onSaleCount = onSaleCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}

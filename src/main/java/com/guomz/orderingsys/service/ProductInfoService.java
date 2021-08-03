package com.guomz.orderingsys.service;

import com.guomz.orderingsys.entity.ProductInfo;

import java.util.List;

public interface ProductInfoService {

    /**
     * 查询上架商品
     * @return
     */
    List<ProductInfo> getProductInfoOnSale();
}

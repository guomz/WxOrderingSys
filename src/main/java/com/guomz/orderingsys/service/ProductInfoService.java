package com.guomz.orderingsys.service;

import com.guomz.orderingsys.entity.OrderDetail;
import com.guomz.orderingsys.entity.ProductInfo;

import java.util.List;

public interface ProductInfoService {

    /**
     * 查询上架商品
     * @return
     */
    List<ProductInfo> getProductInfoOnSale();

    /**
     * 恢复商品库存
     * @param orderDetailList
     */
    void resumeStock(List<OrderDetail> orderDetailList);

    ProductInfo getProductInfoNotNull(String productId);
}

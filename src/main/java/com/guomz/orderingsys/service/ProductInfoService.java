package com.guomz.orderingsys.service;

import com.guomz.orderingsys.domain.dto.CartDto;
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
     * @param cartDtoList
     */
    void resumeStock(List<CartDto> cartDtoList);

    ProductInfo getProductInfoNotNull(String productId);
}

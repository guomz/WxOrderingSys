package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.entity.ProductInfo;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String product_id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String product_id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
}
package com.guomz.orderingsys.service.impl;

import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.domain.condition.ProductInfoCondition;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.enums.ProductStatusEnum;
import com.guomz.orderingsys.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getProductInfoOnSale() {
        ProductInfoCondition condition = new ProductInfoCondition();
        condition.setProductStatus(ProductStatusEnum.ON_SALE.getCode());
        List<ProductInfo> productInfoList = productInfoMapper.selectByCondition(condition);
        return productInfoList;
    }
}

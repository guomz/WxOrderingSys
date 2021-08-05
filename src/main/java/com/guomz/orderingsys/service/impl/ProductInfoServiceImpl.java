package com.guomz.orderingsys.service.impl;

import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.domain.condition.ProductInfoCondition;
import com.guomz.orderingsys.domain.condition.ProductStockChangeCondition;
import com.guomz.orderingsys.entity.OrderDetail;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.enums.ProductStatusEnum;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.exception.BusinessException;
import com.guomz.orderingsys.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Override
    public void resumeStock(List<OrderDetail> orderDetailList) {
        //检查商品并组合库存变化信息
        List<ProductStockChangeCondition> conditionList = orderDetailList.stream()
                .map(orderDetail -> {
                    ProductStockChangeCondition condition = new ProductStockChangeCondition();
                    condition.setProductId(orderDetail.getProductId());
                    condition.setChangedStock(orderDetail.getProductQuantity());
                    return condition;
                }).collect(Collectors.toList());

        productInfoMapper.changeProductStockByList(conditionList);
    }

    @Override
    public ProductInfo getProductInfoNotNull(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (productInfo == null){
            log.error("商品不存在");
            throw new BusinessException(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        return productInfo;
    }
}

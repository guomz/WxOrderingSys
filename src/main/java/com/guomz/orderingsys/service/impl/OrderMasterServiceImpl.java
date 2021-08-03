package com.guomz.orderingsys.service.impl;

import com.guomz.orderingsys.dao.OrderDetailMapper;
import com.guomz.orderingsys.dao.OrderMasterMapper;
import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.domain.dto.CartDto;
import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.entity.OrderDetail;
import com.guomz.orderingsys.entity.OrderMaster;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.enums.OrderStatusEnum;
import com.guomz.orderingsys.enums.PayStatusEnum;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.exception.BusinessException;
import com.guomz.orderingsys.service.OrderMasterService;
import com.guomz.orderingsys.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Resource
    private OrderMasterMapper orderMasterMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        //找出对应商品对比购物车信息
        List<String> productIdList = orderDto.getCartList().stream().map(CartDto::getProductId).distinct().collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoMapper.selectByIdList(productIdList);
        checkOrderProduct(orderDto, productInfoList);
        //计算总金额并比较
        BigDecimal totalAmount = orderDto.getCartList().stream().map(item -> item.getProductPrice().multiply(item.getProductQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAmount.compareTo(orderDto.getOrderAmount()) != 0){
            log.error("订单金额不正确");
            throw new BusinessException(ResponseEnum.ORDER_PRICE_NOT_CORRECT);
        }
        //生成订单与订单明细
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMaster.setOrderId(KeyUtil.generateKey());
        orderMaster.setOrderAmount(totalAmount);
        List<OrderDetail> orderDetailList = orderDto.getCartList().stream()
                .map(item -> {
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(item, orderDetail);
                    orderDetail.setOrderDetailId(KeyUtil.generateKey());
                    orderDetail.setOrderId(orderMaster.getOrderId());
                    return orderDetail;
                }).collect(Collectors.toList());
        //插入数据
        //扣减库存
        return orderDto;
    }

    /**
     * 检查商品是否存在以及价格是否正确
     * @param orderDto
     * @param productInfoList
     */
    private void checkOrderProduct(OrderDto orderDto, List<ProductInfo> productInfoList){
        for (CartDto cart : orderDto.getCartList()) {
            ProductInfo productInfo = productInfoList.stream().filter(item -> item.getProductId().equals(cart.getProductId())).collect(Collectors.toList())
                    .stream().findFirst().orElseThrow(()-> new BusinessException(ResponseEnum.PRODUCT_NOT_EXIST));

            if (productInfo.getProductPrice().compareTo(cart.getProductPrice()) != 0){
                log.error("商品价格不正确");
                throw new BusinessException(ResponseEnum.PRODUCT_PRICE_NOT_VALID);
            }
        }
    }
}

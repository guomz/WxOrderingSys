package com.guomz.orderingsys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.guomz.orderingsys.service.ProductInfoService;
import com.guomz.orderingsys.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductInfoService productInfoService;

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
        orderMasterMapper.insert(orderMaster);
        orderDetailMapper.insertOrderDetailList(orderDetailList);
        //扣减库存

        return orderDto;
    }

    @Override
    public OrderDto getOrderById(String orderId) {
        OrderMaster orderMaster = getOrderMasterNotNull(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectDetailByOrderId(orderId);
        if (orderDetailList.isEmpty()){
            log.error("订单明细不存在");
            throw new BusinessException(ResponseEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        List<CartDto> cartDtoList = orderDetailList.stream()
                .map(item -> {
                    CartDto cartDto = new CartDto();
                    BeanUtils.copyProperties(item, cartDto);
                    return cartDto;
                }).collect(Collectors.toList());

        orderDto.setCartList(cartDtoList);
        return orderDto;
    }

    @Override
    public PageInfo<OrderDto> getOrderListByOpenId(Integer pageNum, Integer pageSize, String openid) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderMaster> orderMasterList = orderMasterMapper.selectOrderByOpenId(openid);
        PageInfo pageInfo = new PageInfo(orderMasterList);

        List<OrderDto> orderDtoList = orderMasterList.stream()
                .map(item -> {
                    OrderDto orderDto = new OrderDto();
                    BeanUtils.copyProperties(item, orderDto);
                    return orderDto;
                }).collect(Collectors.toList());

        orderDtoList.forEach(item -> {
            List<OrderDetail> orderDetailList = orderDetailMapper.selectDetailByOrderId(item.getOrderId());
            List<CartDto> cartDtoList = orderDetailList.stream()
                    .map(orderDetailItem -> {
                        CartDto cartDto = new CartDto();
                        BeanUtils.copyProperties(orderDetailItem, cartDto);
                        return cartDto;
                    }).collect(Collectors.toList());
            item.setCartList(cartDtoList);
        });
        pageInfo.setList(orderDtoList);
        return pageInfo;
    }

    @Override
    @Transactional
    public OrderDto cancelOrder(OrderDto orderDto) {
        OrderMaster orderMaster = getOrderMasterNotNull(orderDto.getOrderId());
        //判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态不正确,{}", orderMaster.getOrderId());
            throw new BusinessException(ResponseEnum.ORDER_STATUS_NOT_CORRECT);
        }
        //判断订单明细
        orderDto.getCartList().forEach(cartDto -> {
            OrderDetail orderDetail = orderDetailMapper.selectByPrimaryKey(cartDto.getOrderDetailId());
            if (orderDetail == null){
                log.error("订单明细不存在,{}", orderDetail.getOrderDetailId());
                throw new BusinessException(ResponseEnum.ORDER_DETAIL_NOT_EXIST);
            }
            if (!orderDetail.getProductId().equals(cartDto.getProductId())
                    || orderDetail.getProductQuantity().compareTo(cartDto.getProductQuantity()) != 0) {
                log.error("订单中商品信息不正确,{}", orderDetail.getOrderDetailId());
                throw new BusinessException(ResponseEnum.ORDER_DETAIL_PRODUCT_INFO_NOT_CORRECT);
            }
        });
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
        //增加库存
        productInfoService.resumeStock(orderDto.getCartList());
        //如果订单已支付需要退款

        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finishOrder(OrderDto orderDto) {
        //找到订单数据
        OrderMaster orderMaster = getOrderMasterNotNull(orderDto.getOrderId());
        //修改订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW)){
            log.error("订单状态不正确");
            throw new BusinessException(ResponseEnum.ORDER_STATUS_NOT_CORRECT);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderDto.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

        return orderDto;
    }

    @Override
    public OrderDto payOrder(OrderDto orderDto) {
        return null;
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

    @Override
    public OrderMaster getOrderMasterNotNull(String orderId){
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (orderMaster == null){
            log.error("订单不存在"  );
            throw new BusinessException(ResponseEnum.ORDER_NOT_EXIST);
        }
        return orderMaster;
    }
}

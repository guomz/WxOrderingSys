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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
        Map<String, ProductInfo> productInfoMap = productInfoMapper.selectByIdList(productIdList).stream()
                .collect(Collectors.toMap(ProductInfo::getProductId, Function.identity(), (k1,k2)->k2));
        checkOrderProduct(orderDto, productInfoMap);
        //计算总金额并比较
        BigDecimal totalAmount = orderDto.getCartList().stream()
                .map(item -> item.getProductPrice().multiply(item.getProductQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAmount.compareTo(orderDto.getOrderAmount()) != 0){
            log.error("订单金额不正确");
            throw new BusinessException(ResponseEnum.ORDER_PRICE_NOT_CORRECT);
        }
        //生成订单与订单明细
        OrderMaster orderMaster = generateOrderMaster(orderDto, totalAmount);
        List<OrderDetail> orderDetailList = generateOrderDetailList(orderDto, orderMaster, productInfoMap);
        //插入数据
        orderMasterMapper.insert(orderMaster);
        orderDetailMapper.insertOrderDetailList(orderDetailList);
        //扣减库存

        return orderDto;
    }

    /**
     * 生成一批订单明细
     * @param orderDto
     * @param orderMaster
     * @param productInfoMap
     * @return
     */
    private List<OrderDetail> generateOrderDetailList(OrderDto orderDto, OrderMaster orderMaster, Map<String, ProductInfo> productInfoMap) {
        List<OrderDetail> orderDetailList = orderDto.getCartList().stream()
                .map(item -> {
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(item, orderDetail);
                    orderDetail.setOrderDetailId(KeyUtil.generateKey());
                    orderDetail.setOrderId(orderMaster.getOrderId());
                    orderDetail.setProductIcon(productInfoMap.get(item.getProductId()).getProductIcon());
                    orderDetail.setCreateTime(new Date());
                    orderDetail.setUpdateTime(new Date());
                    item.setOrderId(orderMaster.getOrderId());
                    item.setOrderDetailId(orderDetail.getOrderDetailId());
                    return orderDetail;
                }).collect(Collectors.toList());
        return orderDetailList;
    }

    /**
     * 生成主订单
     * @param orderDto
     * @param totalAmount
     * @return
     */
    private OrderMaster generateOrderMaster(OrderDto orderDto, BigDecimal totalAmount) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMaster.setOrderId(KeyUtil.generateKey());
        orderMaster.setOrderAmount(totalAmount);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderDto.setOrderId(orderMaster.getOrderId());
        orderDto.setOrderStatus(orderMaster.getOrderStatus());
        orderDto.setPayStatus(orderMaster.getPayStatus());
        return orderMaster;
    }

    @Override
    public OrderDto getOrderById(String orderId) {
        OrderMaster orderMaster = getOrderMasterNotNull(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectDetailByOrderId(orderId);
        if (orderDetailList.isEmpty()){
            log.error("订单明细不存在");
            throw new BusinessException(ResponseEnum.ORDER_DETAIL_NOT_EXIST);
        }
        //封装dto
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
        //封装dto
        List<OrderDto> orderDtoList = generateOrderDtoList(orderMasterList);
        pageInfo.setList(orderDtoList);
        return pageInfo;
    }

    /**
     * 生成一组orderdto
     * @param orderMasterList
     * @return
     */
    private List<OrderDto> generateOrderDtoList(List<OrderMaster> orderMasterList) {
        List<OrderDto> orderDtoList = orderMasterList.stream()
                .map(item -> {
                    OrderDto orderDto = new OrderDto();
                    BeanUtils.copyProperties(item, orderDto);
                    return orderDto;
                }).collect(Collectors.toList());

        for (OrderDto orderDto : orderDtoList) {
            List<OrderDetail> orderDetailList = orderDetailMapper.selectDetailByOrderId(orderDto.getOrderId());
            List<CartDto> cartDtoList = orderDetailList.stream()
                    .map(orderDetailItem -> {
                        CartDto cartDto = new CartDto();
                        BeanUtils.copyProperties(orderDetailItem, cartDto);
                        return cartDto;
                    }).collect(Collectors.toList());
            orderDto.setCartList(cartDtoList);
        }
        return orderDtoList;
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
        checkCartDtoList(orderDto);
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMaster.setUpdateTime(new Date());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
        //增加库存
        productInfoService.resumeStock(orderDto.getCartList());
        //如果订单已支付需要退款

        return orderDto;
    }

    /**
     * 检查订单明细的商品信息
     * @param orderDto
     */
    private void checkCartDtoList(OrderDto orderDto) {
        for (CartDto cartDto : orderDto.getCartList()) {
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
        }
    }

    @Override
    @Transactional
    public OrderDto finishOrder(OrderDto orderDto) {
        //找到订单数据
        OrderMaster orderMaster = getOrderMasterNotNull(orderDto.getOrderId());
        //判断并修改订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态不正确");
            throw new BusinessException(ResponseEnum.ORDER_STATUS_NOT_CORRECT);
        }
        if (!orderMaster.getPayStatus().equals(PayStatusEnum.PAID.getCode())){
            log.error("订单支付状态错误，{}", orderDto.getOrderId());
            throw new BusinessException(ResponseEnum.ORDER_PAY_STATUS_NOT_CORRECT);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderDto.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMaster.setUpdateTime(new Date());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto payOrder(OrderDto orderDto) {
        //查找订单
        OrderMaster orderMaster = getOrderMasterNotNull(orderDto.getOrderId());
        //判断状态
        if (orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态错误，{}", orderDto.getOrderId());
            throw new BusinessException(ResponseEnum.ORDER_STATUS_NOT_CORRECT);
        }
        if (!orderMaster.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
            log.error("订单支付状态错误，{}", orderDto.getOrderId());
            throw new BusinessException(ResponseEnum.ORDER_PAY_STATUS_NOT_CORRECT);
        }
        //修改状态
        orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
        orderDto.setPayStatus(PayStatusEnum.PAID.getCode());
        orderMaster.setUpdateTime(new Date());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
        return orderDto;
    }

    /**
     * 检查商品是否存在以及价格是否正确
     * @param orderDto
     * @param productInfoMap
     */
    private void checkOrderProduct(OrderDto orderDto, Map<String, ProductInfo> productInfoMap){
        for (CartDto cart : orderDto.getCartList()) {
            ProductInfo productInfo = productInfoMap.get(cart.getProductId());
            if (productInfo == null){
                log.error("商品不存在,{}", cart.getProductId());
                throw new BusinessException(ResponseEnum.PRODUCT_NOT_EXIST);
            }
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

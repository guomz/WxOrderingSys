package com.guomz.orderingsys.domain.dto;

import com.guomz.orderingsys.validate_group.CancelOrder;
import com.guomz.orderingsys.validate_group.CreateOrder;
import com.guomz.orderingsys.validate_group.FinishOrder;
import com.guomz.orderingsys.validate_group.PayOrder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    @NotBlank(groups = {CancelOrder.class, FinishOrder.class, PayOrder.class}, message = "订单id为空")
    private String orderId;
    @NotBlank(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "买家姓名为空")
    private String buyerName;
    @NotBlank(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "买家电话为空")
    private String buyerPhone;
    @NotBlank(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "买家地址为空")
    private String buyerAddress;
    @NotBlank(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "买家openid为空")
    private String buyerOpenid;
    @NotNull(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "订单总金额为空")
    private BigDecimal orderAmount;
    @NotNull(groups = {CancelOrder.class, FinishOrder.class, PayOrder.class}, message = "订单状态为空")
    private Integer orderStatus;
    @NotNull(groups = {CancelOrder.class, FinishOrder.class, PayOrder.class}, message = "支付状态为空")
    private Integer payStatus;
    @Valid
    @NotNull(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "订单明细为空")
    List<CartDto> cartList;
}

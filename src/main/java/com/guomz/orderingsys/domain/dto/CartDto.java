package com.guomz.orderingsys.domain.dto;

import com.guomz.orderingsys.validate_group.CancelOrder;
import com.guomz.orderingsys.validate_group.CreateOrder;
import com.guomz.orderingsys.validate_group.FinishOrder;
import com.guomz.orderingsys.validate_group.PayOrder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CartDto {
    @NotBlank(message = "订单明细id为空", groups = {CancelOrder.class, FinishOrder.class, PayOrder.class})
    private String orderDetailId;
    @NotBlank(message = "订单id为空", groups = {CancelOrder.class, FinishOrder.class, PayOrder.class})
    private String orderId;
    @NotNull(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "商品id为空")
    private String productId;
    @NotBlank(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "商品名称为空")
    private String productName;
    @NotNull(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "商品价格为空")
    private BigDecimal productPrice;
    @NotNull(groups = {CreateOrder.class,CancelOrder.class, FinishOrder.class, PayOrder.class},message = "商品数量为空")
    private BigDecimal productQuantity;
}

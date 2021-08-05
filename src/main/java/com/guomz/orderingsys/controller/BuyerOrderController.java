package com.guomz.orderingsys.controller;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.domain.resp.BusinessResponse;
import com.guomz.orderingsys.domain.resp.PageResponse;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.service.OrderMasterService;
import com.guomz.orderingsys.validate_group.CancelOrder;
import com.guomz.orderingsys.validate_group.CreateOrder;
import com.guomz.orderingsys.validate_group.FinishOrder;
import com.guomz.orderingsys.validate_group.PayOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/buyer")
@Validated
@Api(tags = "客户订单服务")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    @ApiOperation("根据id查询订单")
    @GetMapping(value = "/getorderbyid")
    public BusinessResponse<OrderDto> getOrderById(@RequestParam(value = "orderId", required = false) @NotBlank(message = "订单id为空") String orderId){
        OrderDto orderDto = orderMasterService.getOrderById(orderId);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }

    /**
     * 根据openid查询订单
     * @param pageNum
     * @param pageSize
     * @param openid
     * @return
     */
    @ApiOperation("根据openid查询订单")
    @GetMapping(value = "/getorderbyopenid")
    public PageResponse<OrderDto> getOrderByOpenid(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "openid", required = false) String openid){
        PageInfo<OrderDto> pageInfo = orderMasterService.getOrderListByOpenId(pageNum, pageSize, openid);
        return new PageResponse<>(ResponseEnum.OK, pageInfo);
    }

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    @ApiOperation("创建订单")
    @PostMapping("/createorder")
    public BusinessResponse<OrderDto> createOrder(@Validated(CreateOrder.class) OrderDto orderDto){

        orderDto = orderMasterService.createOrder(orderDto);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }

    @ApiOperation("取消订单")
    @PostMapping("/cancelorder")
    public BusinessResponse<OrderDto> cancelOrder(@Validated(CancelOrder.class) OrderDto orderDto){
        orderDto = orderMasterService.cancelOrder(orderDto);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }

    @ApiOperation("完成订单")
    @PostMapping("/finishorder")
    public BusinessResponse<OrderDto> finishOrder(@Validated(FinishOrder.class) OrderDto orderDto){
        orderDto = orderMasterService.finishOrder(orderDto);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }

    @ApiOperation("支付订单")
    @PostMapping("/payorder")
    public BusinessResponse<OrderDto> payOrder(@Validated(PayOrder.class) OrderDto orderDto){
        orderDto = orderMasterService.payOrder(orderDto);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }
}

package com.guomz.orderingsys.controller;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.domain.resp.BusinessResponse;
import com.guomz.orderingsys.domain.resp.PageResponse;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.service.OrderMasterService;
import com.guomz.orderingsys.validate_group.CreateOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer")
@Validated
@Api(tags = "客户订单服务")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @ApiOperation("根据id查询订单")
    @GetMapping(value = "/getorderbyid")
    public BusinessResponse<OrderDto> getOrderById(@RequestParam(value = "orderId", required = false) @NotBlank(message = "订单id为空") String orderId,
                                                   @RequestParam(value = "openid", required = false) @NotBlank(message = "openid为空") String openid){
        OrderDto orderDto = orderMasterService.getOrderByIdCheck(orderId,openid);
        return new BusinessResponse<>(ResponseEnum.OK, orderDto);
    }

    @ApiOperation("根据openid查询订单")
    @GetMapping(value = "/getorderbyopenid")
    public PageResponse<OrderDto> getOrderByOpenid(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "openid", required = false) String openid){
        PageInfo<OrderDto> pageInfo = orderMasterService.getOrderListByOpenId(pageNum, pageSize, openid);
        return new PageResponse<>(ResponseEnum.OK, pageInfo);
    }

    @ApiOperation("创建订单")
    @PostMapping("/createorder")
    public BusinessResponse<Map<String, String>> createOrder(@Validated(CreateOrder.class) @RequestBody OrderDto orderDto){

        String orderId = orderMasterService.createOrder(orderDto);
        Map<String,String> result = new HashMap<>();
        result.put("orderId", orderId);
        return new BusinessResponse<>(ResponseEnum.OK, result);
    }

    @ApiOperation("取消订单")
    @PostMapping("/cancelorder")
    public BusinessResponse cancelOrder(@RequestParam(value = "orderId", required = false) @NotBlank(message = "订单id为空") String orderId,
                                        @RequestParam(value = "openid", required = false) @NotBlank(message = "openid为空") String openid){
        orderMasterService.cancelOrder(orderId, openid);
        return new BusinessResponse<>(ResponseEnum.OK);
    }

    @ApiOperation("完成订单")
    @PostMapping("/finishorder")
    public BusinessResponse finishOrder(@RequestParam(value = "orderId", required = false) @NotBlank(message = "订单id为空") String orderId,
                                        @RequestParam(value = "openid", required = false) @NotBlank(message = "openid为空") String openid){
       orderMasterService.finishOrder(orderId, openid);
        return new BusinessResponse<>(ResponseEnum.OK);
    }

    @ApiOperation("支付订单")
    @PostMapping("/payorder")
    public BusinessResponse payOrder(@RequestParam(value = "orderId", required = false) @NotBlank(message = "订单id为空") String orderId,
                                     @RequestParam(value = "openid", required = false) @NotBlank(message = "openid为空") String openid){
        orderMasterService.payOrder(orderId, openid);
        return new BusinessResponse<>(ResponseEnum.OK);
    }
}

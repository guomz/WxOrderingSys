package com.guomz.orderingsys.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.guomz.orderingsys.domain.resp.BusinessResponse;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.service.AlipayService;
import com.guomz.orderingsys.util.AlipayParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
@Slf4j
@Api(tags = "支付宝相关")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    /**
     * 支付订单，位于支付页面a标签中
     * @param response
     * @throws IOException
     */
    @GetMapping("/payorder")
    public void payOrder(HttpServletResponse response) throws IOException {
        String result = alipayService.payOrder("8");
        response.setContentType("text/html;charset=utf-8");
        //直接将完整的表单html输出到页面
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 接收支付后支付结果异步通知
     * @param request
     * @return
     */
    @PostMapping("/paynotify")
    @ResponseBody
    public String payNotify(HttpServletRequest request){
        log.info("支付结果异步通知");
        Map<String,String> params = AlipayParamUtil.getAlipayParams(request);
        try {
            if (Factory.Payment.Common().verifyNotify(params)){
                log.error("验签结果失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("验签失败");
        }
        params.forEach((key,value) -> System.out.println(key+", " + value));
        //更改订单状态
        alipayService.changeOrderPayStatus(params.get("out_trade_no"));
        return "success";
    }

    @GetMapping("/checkpayment")
    @ResponseBody
    @ApiOperation("查询支付结果")
    public BusinessResponse<AlipayTradeQueryResponse> checkPayment(@RequestParam("orderId") String orderId){
        AlipayTradeQueryResponse queryResponse = alipayService.checkPayment(orderId);
        return new BusinessResponse<>(ResponseEnum.OK, queryResponse);
    }

}

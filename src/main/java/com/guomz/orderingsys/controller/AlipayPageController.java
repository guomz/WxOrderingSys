package com.guomz.orderingsys.controller;

import com.guomz.orderingsys.util.AlipayParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/alipay")
@Slf4j
public class AlipayPageController {
    /**
     * 转到支付后回调页面
     * @param request
     * @return
     */
    @GetMapping("/payreturn")
    public String payReturn(HttpServletRequest request){
        log.info("支付回调");
        Map<String,String> params = AlipayParamUtil.getAlipayParams(request);
        log.info(params.toString());
        params.forEach((key,value) -> System.out.println(key+", " + value));
        return "payreturn";
    }

    /**
     * 前往支付页面
     * @return
     */
    @GetMapping(value = "/paypage")
    public String payPage(){
        return "paypage";
    }
}

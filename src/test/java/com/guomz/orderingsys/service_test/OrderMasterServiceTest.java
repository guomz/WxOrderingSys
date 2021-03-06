package com.guomz.orderingsys.service_test;
import java.math.BigDecimal;
import com.guomz.orderingsys.domain.dto.CartDto;
import java.util.ArrayList;
import java.util.Arrays;

import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.service.impl.OrderMasterServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderMasterServiceTest {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @Test
    public void testCreateOrder(){
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("guomz");
        orderDto.setBuyerPhone("123123123");
        orderDto.setBuyerAddress("home-ddd");
        orderDto.setBuyerOpenid("555555555666666");
        orderDto.setOrderAmount(new BigDecimal("10"));
        orderDto.setCartList(new ArrayList<CartDto>());

        CartDto cartDto = new CartDto();
        cartDto.setProductId("1627989001484");
        cartDto.setProductName("汉堡");
        cartDto.setProductPrice(new BigDecimal("5"));
        cartDto.setProductQuantity(new BigDecimal("2"));

        orderDto.setCartList(Arrays.asList(cartDto));

        orderMasterService.createOrder(orderDto);
    }

    @Test
    public void testFindByOrderId(){
       // System.out.println(orderMasterService.getOrderById("1628041097587"));
    }

    @Test
    public void testFindByOpenId(){
        System.out.println(orderMasterService.getOrderListByOpenId(1,10,"wx123123123123"));
    }

    @Test
    public void testCanCelOrder(){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId("1628041097587");
        orderDto.setBuyerName("guomz");
        orderDto.setBuyerPhone("123123123");
        orderDto.setBuyerAddress("home-ddd");
        orderDto.setBuyerOpenid("555555555666666");
        orderDto.setOrderAmount(new BigDecimal("10"));

        CartDto cartDto = new CartDto();
        cartDto.setOrderId("1628041097587");
        cartDto.setOrderDetailId("1628041098835");
        cartDto.setProductId("1627989001484");
        cartDto.setProductName("汉堡");
        cartDto.setProductPrice(new BigDecimal("5"));
        cartDto.setProductQuantity(new BigDecimal("2"));

        orderDto.setCartList(Arrays.asList(cartDto));
    }
}

package com.guomz.orderingsys.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo {
    private String orderId;
    private BigDecimal orderAmount;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhone;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}

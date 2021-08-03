package com.guomz.orderingsys.exception;

import com.guomz.orderingsys.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private String code;
    private String msg;

    public BusinessException(ResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMessage();
    }
}

package com.guomz.orderingsys.domain.resp;

import com.guomz.orderingsys.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessResponse<T> extends BaseResponse{
    private T data;

    public BusinessResponse(ResponseEnum responseEnum){
        this.setCode(responseEnum.getCode());
        this.setMsg(responseEnum.getMessage());
    }

    public BusinessResponse(ResponseEnum responseEnum, T data){
        this.setCode(responseEnum.getCode());
        this.setMsg(responseEnum.getMessage());
        this.data = data;
    }
}

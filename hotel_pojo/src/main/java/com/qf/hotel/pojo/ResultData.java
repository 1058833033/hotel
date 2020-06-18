package com.qf.hotel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.omg.CORBA.INTERNAL;

/**
 * @author ChenJie
 * @date 2020-05-18 12:38:00
 * 功能说明:对外返回数据的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultData<T> {
    // 请求响应码
    private Integer code;

    // 请求响应信息
    private String msg;

    // 请求响应数据
    private T data;

    public static interface Code{
        Integer CODE_SUCCESS = 200;
        Integer CODE_ERROR = 201;
        Integer CODE_RELOGIN = 401;
    }

}

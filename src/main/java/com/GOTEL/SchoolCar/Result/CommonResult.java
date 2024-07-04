package com.GOTEL.SchoolCar.Result;

import com.GOTEL.SchoolCar.Result.Code.ICode;
import com.GOTEL.SchoolCar.Result.Code.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;
    private String token;

    protected CommonResult(int code, String message, T data, String token) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    protected CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> successLogin(T data,String message, String token) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data, token);
    }

    public static <T> CommonResult<T> error(ICode iCode) {
        return new CommonResult<T>(iCode.getCode(), iCode.getMessage(), null);
    }

    public static <T> CommonResult<T> error(int code) {
        return new CommonResult<T>(code, "失败", null);
    }

    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<T>(ResultCode.ERROR.getCode(), message, null);
    }

    public static <T> CommonResult<T> error() {
        return error(ResultCode.ERROR);
    }
}

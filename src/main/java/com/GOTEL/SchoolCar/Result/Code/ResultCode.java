package com.GOTEL.SchoolCar.Result.Code;

import lombok.Getter;

@Getter
public enum ResultCode implements ICode {
    SUCCESS(200, "操作成功", ""),
    ERROR(500, "操作失败", "");

    private int code;
    private String message;
    private String token;

    ResultCode(int code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }
}

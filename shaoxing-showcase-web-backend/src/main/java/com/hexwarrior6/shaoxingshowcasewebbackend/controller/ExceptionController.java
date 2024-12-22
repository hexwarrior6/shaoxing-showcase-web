package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import jakarta.servlet.ServletException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@ControllerAdvice
public class ExceptionController {

    /**
     * 异常处理器方法，用于捕获并处理所有异常。
     *
     * @param e 捕获的异常对象
     * @return 返回封装了错误信息的RestBean对象
     */
    @ExceptionHandler(Exception.class)
    public RestBean<String> error(Exception e) {
        // 处理NoHandlerFoundException异常，通常代表404未找到资源错误
        if (e instanceof NoHandlerFoundException exception)
            // 返回404状态码和异常信息
            return RestBean.failure(404, e.getMessage());
            // 处理ServletException异常，通常代表客户端请求错误
            //其他的Servlet异常就返回400状态码
        else if (e instanceof ServletException exception)
            // 返回400状态码和异常信息
            return RestBean.failure(400, e.getMessage());
        else
            // 处理其他所有异常，通常代表服务器内部错误
            //其他的异常就返回500状态码
            return RestBean.failure(500, e.getMessage());
    }

}
package com.wgx.dormitorymanager2.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * author:wgx
 * version:1.0
 */
@ControllerAdvice//拦截所有的Controller控制器中方法中的所有Exception级别的异常
public class ControllerExceptionHandler {
    @ExceptionHandler({Exception.class})//拦截所有的Exception异常
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        ModelAndView mv = new ModelAndView();
        //获取异常的相关信息
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //返回自定义的异常界面
        mv.setViewName("error/error");
        return mv;
    }
}

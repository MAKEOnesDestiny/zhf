package com.leqee.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletResponse;

public class MyResolver implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

    private ServletModelAttributeMethodProcessor delegate = new ServletModelAttributeMethodProcessor(false);

    static final ThreadLocal<Boolean> processed = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MyModelAttribute.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object obj = null;
        try {
            obj = delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        } catch (BindException e) {
            HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
            JSONObject resultJ = new JSONObject();
            resultJ.put("code", -1);
            resultJ.put("message", "错误的数据123");
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            response.getOutputStream().write(resultJ.toString().getBytes("UTF-8"));
//            response.setContentLength(response.toString().getBytes("UTF-8").length);
            response.flushBuffer();
            processed.set(true);
        }
        return obj;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return false;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

    }
}

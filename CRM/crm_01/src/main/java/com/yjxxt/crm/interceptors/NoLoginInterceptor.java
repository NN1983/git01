package com.yjxxt.crm.interceptors;

import com.yjxxt.crm.exceptions.NoLoginException;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //未登录拦截，抛异常
        Integer userID = LoginUserUtil.releaseUserIdFromCookie(request);
        if(userID == null || userService.selectByPrimaryKey(userID) == null){
            throw new NoLoginException("用户未登录");
        }

        return true;
    }
}

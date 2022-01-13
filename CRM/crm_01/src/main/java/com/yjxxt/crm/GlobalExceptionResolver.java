package com.yjxxt.crm;

import com.alibaba.fastjson.JSON;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.exceptions.NoLoginException;
import com.yjxxt.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object handler, Exception e) {

        //未登录异常
        if(e instanceof NoLoginException){
            ModelAndView mav = new ModelAndView("redirect:/index");
            return mav;
        }

        ModelAndView mav = new ModelAndView("error");

        mav.addObject("code",400);
        mav.addObject("msg","系统异常，请稍后再试......");

        if (handler instanceof HandlerMethod){

            HandlerMethod handlerMethod =(HandlerMethod) handler;
            ResponseBody responseBody= handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);

            if(responseBody == null){
                //返回视图名称
                if (e instanceof ParamsException){
                    ParamsException pe = (ParamsException) e;
                    mav.addObject("code",pe.getCode());
                    mav.addObject("msg",pe.getMsg());
                }
                return mav;
            }else {
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("参数异常");

                if (e instanceof ParamsException){
                    ParamsException pe = (ParamsException) e;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }

                //响应resultInfo
                httpServletResponse.setContentType("application/json;charset=utf-8");
                //得到输出流
                PrintWriter pw = null;

                try{
                    pw = httpServletResponse.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                } catch (IOException ie){
                    ie.printStackTrace();
                } finally {
                    if(pw != null){
                        pw.close();
                    }
                }
                return null;
            }
        }
        return mav;
    }
}

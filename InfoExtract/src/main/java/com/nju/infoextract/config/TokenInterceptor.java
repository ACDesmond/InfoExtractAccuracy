package com.nju.infoextract.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, Object> res = new HashMap<>();
        System.out.println("请求的地址是： " + request.getContextPath());
        String token = request.getHeader("X-Token");
        System.out.println("拦截器的token： " + token);

        if (StringUtils.isBlank(token)){
            //50008 非法令牌
            res.put("code", 50008);
            returnJson(response, JSON.toJSONString(res));
            System.out.println("请求没有携带token");
            return false;
        }

        //todo 过期令牌

        //把token放在attribute中
        request.setAttribute("token", token);
        System.out.println("放行，有token");
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

package cn.xuzilin.common.utils;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static void save(HttpServletRequest request,String key, Object o){
        HttpSession session = request.getSession();
        session.setAttribute(key,o);
    }
    public static <K> K get(HttpServletRequest request,String key,Class<K> clazz){
        K res = (K) request.getAttribute(key);
        return res;
    }
}

package cn.xuzilin.core.shiro.filters;


import cn.xuzilin.common.vo.MessageVo;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/25.
 * This in ssmjavaconfig, samson.core.shiro.filter
 */
public class ShiroFilterUtil {
    private static final Class<? extends ShiroFilterUtil> CLAZZ = ShiroFilterUtil.class;
    //登录页面
    public static final String LOGIN_URL = "/open/page/loginPage";

    public static final String PHONE_URL = "/open/phone";

    private static final String[] opens = {"/open"};

    public static String getUrl(ServletRequest request) {
        return ((HttpServletRequest) request).getRequestURL().toString();
    }
    /**
     * 判断请求是不是ajax发出
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {

        return "XMLHttpRequest".equalsIgnoreCase(
                ((HttpServletRequest) request).getHeader("X-Requested-With") );
    }

    public static boolean isOpen(ServletRequest request) {

        boolean flag = false;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();

        for (String open : opens) {
            if (uri.startsWith(open)) flag = true;
        }

        return flag;
    }
    public static void writeJsonToResponse(ServletResponse response, MessageVo message) {
        if (message == null || message.getStatus() == null) return;

        writeTo(response, message);
    }

    public static void writeJsonToResponse(ServletResponse response, Map<Object, Object> map) {
        if (map == null || map.size() == 0) return;

        writeTo(response, map);
    }

    private static void writeTo(ServletResponse response, Object object) {

        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(JSON.toJSONString(object));
        } catch (IOException e) {
//            LoggerUtil.fmtError(CLAZZ, e, "获取response的writer时出错");
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }
}

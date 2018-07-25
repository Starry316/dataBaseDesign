package cn.xuzilin.core.shiro.filters;


import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.core.shiro.token.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录filters
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object o) throws Exception {
        StudentEntity token = TokenManager.getStudentToken();
        if (token != null) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {

        if (ShiroFilterUtil.isAjax(servletRequest)) {
            Map<Object, Object> map = new HashMap<>();
            map.put("status",500);
            map.put("message", "用户未登陆");
            ShiroFilterUtil.writeJsonToResponse(servletResponse, map);
            return false;
        }

        //保存Request和Response 到登录后的链接
        saveRequest(servletRequest);

        WebUtils.issueRedirect(servletRequest, servletResponse, ShiroFilterUtil.LOGIN_URL);

//        LoggerUtil.fmtDebug(getClass(),
//                "目前访问[%s],正在前往登录界面", ShiroFilterUtil.getUrl(servletRequest));
        return false;
    }
}

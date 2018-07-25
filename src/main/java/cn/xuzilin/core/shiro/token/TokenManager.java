package cn.xuzilin.core.shiro.token;

import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.po.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Created by Starry on 2018/7/15.
 */
public class TokenManager {


    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }



    public static StudentEntity getStudentToken(){
        return (StudentEntity) get("student");
    }
    public static void studentLogin(StudentEntity student){
        logout();
        save("student",student);
    }
    public static void studentLogout(){
        getAndRemove("student");
    }

    public static UserEntity getUserToken(){
        return (UserEntity) getSubject().getPrincipal();
    }
    public static void login(UserEntity user){
        UserToken token = new UserToken();
        token.setAccount(user.getAccount());
        token.setPass(user.getPassword());
        token.setRememberMe(false);
        getSubject().login(token);
    }
    public static void logout() {
        getSubject().logout();
    }


    public static void save(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object get(Object key) {
        return getSession().getAttribute(key);
    }

    public static Object getAndRemove(Object key) {
        Object ans = getSession().getAttribute(key);
        getSession().removeAttribute(key);
        return ans;
    }

    public static void remove(Object key) {
        getSession().removeAttribute(key);
    }
}

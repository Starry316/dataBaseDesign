package cn.xuzilin.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Starry on 2018/7/16.
 */
public class LoggerUtil {
    /**
     * 输出info信息
     * @param clazz
     * @param message
     */
    public static void info(Class<?> clazz , String message){
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     * @param e			异常类
     */
    public static void error(Class<?> clazz , String message, Exception e){
        Logger logger = LoggerFactory.getLogger(clazz);
        if(null == e){
            logger.error(message);
            return;
        }
        logger.error(message, e);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     */
    public static void error(Class<?> clazz , String message){
        error(clazz, message, null);
    }

}

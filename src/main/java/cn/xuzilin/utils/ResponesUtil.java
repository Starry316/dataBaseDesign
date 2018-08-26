package cn.xuzilin.utils;

import cn.xuzilin.vo.MessageVo;

/**
 * Created by Starry on 2018/7/16.
 */
public class ResponesUtil {
    public static MessageVo success(String mes){
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(200);
        messageVo.setMessage(mes);
        return messageVo;
    }
    public static MessageVo systemError(String mes){
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(500);
        messageVo.setMessage(mes);
        return messageVo;
    }
    public static MessageVo success(String mes ,Object data){
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(200);
        messageVo.setMessage(mes);
        messageVo.setData(data);
        return messageVo;
    }
    public static MessageVo systemError(String mes ,Object data){
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(500);
        messageVo.setMessage(mes);
        messageVo.setData(data);
        return messageVo;
    }
}

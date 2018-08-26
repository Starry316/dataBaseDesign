package cn.xuzilin.vo;

import java.util.Date;

/**
 * Created by Starry on 2018/7/16.
 */
public class MessageVo {
    private int status;
    private String message;
    private Object data;
    private Date time = new Date();


    public Integer getStatus() {
        return status;
    }

    public MessageVo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MessageVo setData(Object data) {
        this.data = data;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public MessageVo setTime(Date time) {
        this.time = time;
        return this;
    }
}

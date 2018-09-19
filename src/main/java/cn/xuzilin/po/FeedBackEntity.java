package cn.xuzilin.po;

import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.SwitchUtil;

import java.util.Date;

public class FeedBackEntity {
    private Integer id;

    private Integer userId;

    private Byte status;

    private Integer managerId;

    private Date submitTime;

    private Date dealTime;

    private String content;

    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
        setStatusName(SwitchUtil.switchFeedbackStatusName(status));
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getSubmitTime() {
        return DateUtil.formatDate(submitTime);
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
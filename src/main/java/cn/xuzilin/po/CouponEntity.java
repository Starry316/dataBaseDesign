package cn.xuzilin.po;

import cn.xuzilin.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class CouponEntity {
    private Integer id;

    private Integer userId;

    private Integer recordId;

    private Byte status;

    private Date expiry;

    private BigDecimal discount;

    private String code;

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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getExpiry() {
        return DateUtil.formatDate(expiry);
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}
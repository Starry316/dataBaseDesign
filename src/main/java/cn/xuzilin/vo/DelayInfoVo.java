package cn.xuzilin.vo;

import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.SwitchUtil;

import java.math.BigDecimal;
import java.util.Date;

public class DelayInfoVo {
    private Integer roomId;
    private Byte roomType;
    private String checkInTime;
    private String checkOutTime;
    private BigDecimal payment;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Byte getRoomType() {
        return roomType;
    }

    public void setRoomType(Byte roomType) {
        this.roomType = roomType;
        this.setTypeName(SwitchUtil.switchType(roomType));
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = DateUtil.formatDate(checkInTime);
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime =  DateUtil.formatDate(checkOutTime);
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private String typeName;
}

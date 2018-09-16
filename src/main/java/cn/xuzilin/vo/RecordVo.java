package cn.xuzilin.vo;

import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.SwitchUtil;

import java.math.BigDecimal;
import java.util.Date;

public class RecordVo {
    private Integer id;

    private Integer roomId;

    private Date checkInTime;

    private Date checkOutTime;

    private BigDecimal payment;

    private Integer memberCardId;

    private Byte roomType;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCheckInTime() {
        return DateUtil.formatDate(checkInTime);
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return DateUtil.formatDate(checkOutTime);
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Integer memberCardId) {
        this.memberCardId = memberCardId;
    }

    public Byte getRoomType() {
        return roomType;
    }

    public void setRoomType(Byte roomType) {
        this.roomType = roomType;
        setTypeName(SwitchUtil.switchType(roomType));
    }
}

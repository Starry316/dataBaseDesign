package cn.xuzilin.common.po;

import java.util.Date;

public class ReserveEntity {
    private Integer id;

    private Integer roomId;

    private Date reserveCheckInTime;

    private String phone;

    private String name;

    private Byte status;

    private Date reserveCheckOutTime;

    private Byte reserveType;

    private Integer userId;

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

    public Date getReserveCheckInTime() {
        return reserveCheckInTime;
    }

    public void setReserveCheckInTime(Date reserveCheckInTime) {
        this.reserveCheckInTime = reserveCheckInTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getReserveCheckOutTime() {
        return reserveCheckOutTime;
    }

    public void setReserveCheckOutTime(Date reserveCheckOutTime) {
        this.reserveCheckOutTime = reserveCheckOutTime;
    }

    public Byte getReserveType() {
        return reserveType;
    }

    public void setReserveType(Byte reserveType) {
        this.reserveType = reserveType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
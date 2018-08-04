package cn.xuzilin.common.po;

public class ReserveEntity {
    private Integer id;

    private Integer roomId;

    private String reserveCheckInTime;

    private String phone;

    private String name;

    private Byte status;

    private String reserveCheckOutTime;

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

    public String getReserveCheckInTime() {
        return reserveCheckInTime;
    }

    public void setReserveCheckInTime(String reserveCheckInTime) {
        this.reserveCheckInTime = reserveCheckInTime == null ? null : reserveCheckInTime.trim();
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

    public String getReserveCheckOutTime() {
        return reserveCheckOutTime;
    }

    public void setReserveCheckOutTime(String reserveCheckOutTime) {
        this.reserveCheckOutTime = reserveCheckOutTime == null ? null : reserveCheckOutTime.trim();
    }
}
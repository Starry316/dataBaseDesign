package cn.xuzilin.po;

public class RoomEntity {
    private Integer roomId;

    private Byte roomType;

    private Byte checkIn;

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
    }

    public Byte getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Byte checkIn) {
        this.checkIn = checkIn;
    }
}
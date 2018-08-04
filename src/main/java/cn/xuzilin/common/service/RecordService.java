package cn.xuzilin.common.service;

import cn.xuzilin.common.consts.ConstPool;
import cn.xuzilin.common.dao.RecordEntityMapper;
import cn.xuzilin.common.dao.RoomEntityMapper;
import cn.xuzilin.common.po.RecordEntity;
import cn.xuzilin.common.po.RoomEntity;
import cn.xuzilin.common.utils.DateUtil;
import cn.xuzilin.common.utils.SwitchUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

@Service
public class RecordService {
    @Resource
    private RecordEntityMapper recordMapper;

    @Resource
    private RoomEntityMapper roomMapper;

    @Resource
    private RoomService roomService;

    public int insert(RecordEntity record){
        return recordMapper.insertSelective(record);
    }

    /**
     * 续住 判断续住时间是否比原来时间短
     * @param roomId
     * @param delayCheckOutTime
     * @return
     */
    public boolean delay(int roomId,String delayCheckOutTime){
        RecordEntity record = getByRoomId(roomId);
        if (record == null) return false;
        record.setCheckOutTime(delayCheckOutTime);
        update(record);
        return true;
    }

    /**
     * 退房
     * @param roomId
     */
    public void checkOut(int roomId){
        //更新record记录
        RecordEntity record = recordMapper.getByRoomId(roomId);
        record.setCheckOutTime(DateUtil.getNowDate());
        record.setStatus(ConstPool.CHECK_OUT);
        update(record);

        //更新房间信息
        roomMapper.selectByPrimaryKey(roomId);
        roomService.checkOut(roomId);
    }

    /**
     * 换房
     * @param newRoomId
     * @param oldRoomId
     */
    public void changeRoom(int newRoomId, int oldRoomId){
        RecordEntity oldRoomRecord = getByRoomId(oldRoomId);
        RecordEntity newRoomRecord = new RecordEntity();
        newRoomRecord.setCheckInTime(DateUtil.getNowDate());
        newRoomRecord.setCheckOutTime(oldRoomRecord.getCheckOutTime());
        newRoomRecord.setPhoneNum(oldRoomRecord.getPhoneNum());
        newRoomRecord.setIdcardNo(oldRoomRecord.getIdcardNo());
        newRoomRecord.setCustomerName(oldRoomRecord.getCustomerName());
        newRoomRecord.setStatus(ConstPool.CHECK_IN);
        newRoomRecord.setRoomId(newRoomId);
        recordMapper.insertSelective(newRoomRecord);
        oldRoomRecord.setStatus(ConstPool.CHECK_OUT);
        recordMapper.updateByPrimaryKeySelective(oldRoomRecord);

        roomService.checkOut(oldRoomId);
        roomService.checkIn(newRoomId);
    }

    /**
     * 获取退房时的信息
     * @param roomId
     * @return
     */
    public JSONObject getCheckOutInfo(int roomId){
        JSONObject data = new JSONObject();
        RecordEntity record = recordMapper.getByRoomId(roomId);
        RoomEntity room = roomMapper.selectByPrimaryKey(roomId);
        double paymentPerDay = SwitchUtil.switchTpyePayment(room.getRoomType());
        long days = DateUtil.subDateByDay(DateUtil.getNowDate(),record.getCheckInTime());
        double discount = 59.00;
        data.put("quitCheckInTime",record.getCheckInTime());
        data.put("quitCheckOutTime",DateUtil.getNowDate());
        data.put("paymentPerDay",paymentPerDay);
        data.put("paymentTotal",days*paymentPerDay);
        data.put("discount",discount);
        data.put("actualPayment",days*paymentPerDay-discount);
        return data;
    }


    public RecordEntity getByRoomId(int roomId){
        return recordMapper.getByRoomId(roomId);
    }
    public int update (RecordEntity record){
        return recordMapper.updateByPrimaryKeySelective(record);
    }





}

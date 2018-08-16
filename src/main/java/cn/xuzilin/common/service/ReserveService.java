package cn.xuzilin.common.service;

import cn.xuzilin.common.consts.ConstPool;
import cn.xuzilin.common.dao.ReserveEntityMapper;
import cn.xuzilin.common.dao.RoomEntityMapper;
import cn.xuzilin.common.po.ReserveEntity;
import cn.xuzilin.common.po.RoomEntity;
import cn.xuzilin.common.utils.DateUtil;
import cn.xuzilin.common.utils.JSONUtil;
import cn.xuzilin.common.utils.SwitchUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Starry on 2018/7/25.
 */
@Service
public class ReserveService {
    @Resource
    private ReserveEntityMapper reserveMapper;

    @Resource
    private RoomEntityMapper roomMapper;

    /**
     * 通过状态获取预定列表
     * @param status
     * @return
     */
    public JSONArray getAllReserveByStatus(byte status){
        List<ReserveEntity> list = reserveMapper.getAllByStatus(status);
        JSONArray jsonArray = JSONUtil.toJSONArray(list);
        return jsonArray;
    }

    public JSONArray getReserveDataList(int page){
        PageHelper.startPage(page, 20);
        List<ReserveEntity> list = reserveMapper.getAllByStatus(ConstPool.ACTIVE);
        JSONArray res = new JSONArray();
        for (ReserveEntity i:list){
            JSONObject data = new JSONObject();
            RoomEntity room = roomMapper.selectByPrimaryKey(i.getRoomId());
            data.put("id",i.getId());
            data.put("roomId",i.getRoomId());
            data.put("roomType", SwitchUtil.switchType(room.getRoomType()));
            if (DateUtil.subDateByDay(DateUtil.dateToStr(i.getReserveCheckInTime()),DateUtil.getNowDateStr())<0)
                data.put("roomStatusName","过期未入住");
            else
                data.put("roomStatusName","等待入住");
            data.put("customerName",i.getName());
            data.put("customerPhoneNum",i.getPhone());
            data.put("checkInTime",DateUtil.formatDate(i.getReserveCheckInTime()));
            data.put("checkOutTime",DateUtil.formatDate(i.getReserveCheckOutTime()));
            res.add(data);
        }
        return res;

    }

    /**
     * 通过房间号获取有效的预定信息
     * @param roomId
     * @return
     */
    public ReserveEntity getByRoomId(int roomId){
        return reserveMapper.getByRoomId(roomId);
    }

    /**
     * 返回预定数
     * @return
     */
    public int getCount(){
        return reserveMapper.getCount();
    }

    /**
     * 预定房间
     * @param roomId
     * @param reserveCheckInTime
     * @param reserveCheckOutTime
     * @param phone
     * @param name
     */
    public void reserve(int roomId, String reserveCheckInTime,String reserveCheckOutTime,String phone,String name,int userId){
        ReserveEntity reserve = new ReserveEntity();
        reserve.setReserveCheckInTime(DateUtil.strToDate(reserveCheckInTime));
        reserve.setReserveCheckOutTime(DateUtil.strToDate(reserveCheckOutTime));
        reserve.setRoomId(roomId);
        reserve.setPhone(phone);
        reserve.setName(name);
        reserve.setStatus(ConstPool.ACTIVE);
        reserve.setUserId(userId);

        RoomEntity room = roomMapper.selectByPrimaryKey(roomId);
        reserve.setReserveType(room.getRoomType());
//        room.setCheckIn(ConstPool.RESERVED);
//        roomMapper.updateByPrimaryKeySelective(room);
        reserveMapper.insertSelective(reserve);
    }

    public void cancelReverse(int id){
        reserveMapper.updateStatusById(ConstPool.CANCEL,id);
        ReserveEntity reserve = reserveMapper.selectByPrimaryKey(id);
        Integer roomId = reserve.getRoomId();
        if (roomId == null)return;
        RoomEntity room = roomMapper.selectByPrimaryKey(roomId);
        room.setCheckIn(ConstPool.EMPTY);
        roomMapper.updateByPrimaryKey(room);
    }

    public JSONArray getAllActive(int userId){
        List<ReserveEntity> reserveList = reserveMapper.getActiveByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        for (ReserveEntity i : reserveList){
            JSONObject object= new JSONObject();
            object.put("roomType",SwitchUtil.switchType(i.getReserveType()));
            object.put("checkInTime",DateUtil.dateToStr(i.getReserveCheckInTime()));
            object.put("id",i.getId());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    public void update(ReserveEntity record){
        reserveMapper.updateByPrimaryKeySelective(record);
    }

}

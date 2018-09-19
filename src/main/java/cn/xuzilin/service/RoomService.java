package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.RecordEntityMapper;
import cn.xuzilin.dao.ReserveEntityMapper;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.RecordEntity;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.utils.BigDecimalUtil;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.JSONUtil;
import cn.xuzilin.utils.SwitchUtil;
import cn.xuzilin.vo.RoomRecordVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Starry on 2018/7/25.
 */
@Service
public class RoomService {
    @Resource
    private RoomEntityMapper roomMapper;

    @Resource
    private RecordEntityMapper recordMapper;

    @Resource
    private ReserveEntityMapper reserveMapper;

    public JSONArray getEmptyRoomList(){
        return JSONUtil.toJSONArray(roomMapper.getEmptyRoomList());
    }

    /**
     * 入住该房间
     * @param roomId
     */
    public void checkIn(int roomId){
        RoomEntity roomEntity = roomMapper.selectByPrimaryKey(roomId);
        roomEntity.setCheckIn(ConstPool.FULL);
        roomMapper.updateByPrimaryKey(roomEntity);
    }

    /**
     * 退房
     * @param roomId
     */
    public void checkOut(int roomId){
        RoomEntity roomEntity = roomMapper.selectByPrimaryKey(roomId);
        roomEntity.setCheckIn(ConstPool.EMPTY);
        roomMapper.updateByPrimaryKey(roomEntity);
    }

    /**
     * 获取全部数据
     * @param page
     * @return
     */
    public JSONArray getDataList(int page,
                                 String roomStatus,
                                 String checkInTime,
                                 String checkOutTime,
                                 String roomId,
                                 String customerName,
                                 String customerIdNo){
        String params[]={roomId,roomStatus,checkInTime,checkOutTime,customerName,customerIdNo};
        boolean flags[]=new boolean[6];
        for (int i=0;i<6;i++){
            if (params[i].length() == 0){
                params[i]="0";
                flags[i]=true;
            }
            else flags[i]=false;
        }
        if (params[1].equals("3")) flags[1]=true;
        PageHelper.startPage(page, 15);
        List<RoomRecordVo> list = roomMapper.getData(Integer.parseInt(params[0]),flags[0],
                Byte.parseByte(params[1]),flags[1],
                params[2],flags[2],
                params[3],flags[3],
                params[4],flags[4],
                params[5],flags[5]
                );
        JSONArray res = new JSONArray();
        for (RoomRecordVo i : list){
            JSONObject data = new JSONObject();
            data.put("recordId",i.getId());
            data.put("roomId",i.getRoomId());
            data.put("roomType",SwitchUtil.switchType(i.getRoomType()));
            data.put("roomStatus",i.getCheckIn());
            data.put("roomStatusName",SwitchUtil.switchStatus(i.getCheckIn()));
            if (i.getCheckInTime()!=null){
                data.put("checkInTime",DateUtil.formatDate(i.getCheckInTime()));
                Long days = DateUtil.daysToNow(i.getCheckInTime());
                data.put("payment", BigDecimalUtil.multiply(SwitchUtil.switchTpyePayment(i.getRoomType()),days));
            }
            if (i.getCheckOutTime()!=null)
            data.put("checkOutTime",DateUtil.formatDate(i.getCheckOutTime()));
            res.add(data);
        }
        return res;
    }

    /**
     * 获取最大页数
     * @return
     */
    public int getMaxPage( String roomStatus,
                           String checkInTime,
                           String checkOutTime,
                           String roomId,
                           String customerName,
                           String customerIdNo){
        String params[]={roomId,roomStatus,checkInTime,checkOutTime,customerName,customerIdNo};
        boolean flags[]=new boolean[6];
        for (int i=0;i<6;i++){
            if (params[i].length() == 0){
                params[i]="0";
                flags[i]=true;
            }
            else flags[i]=false;
        }
        if (params[1].equals("3")) flags[1]=true;
        int count = roomMapper.getDataCount(Integer.parseInt(params[0]),flags[0],
                Byte.parseByte(params[1]),flags[1],
                params[2],flags[2],
                params[3],flags[3],
                params[4],flags[4],
                params[5],flags[5]
        );
        int res = (count+14) / 15;
        return res;
    }

    /**
     * 查询所有可以更换的房间
     * @param page
     * @return
     */
    public JSONArray getChangeRoomList(int page){
        PageHelper.startPage(page, 20);
        List<RoomEntity> list = roomMapper.getEmptyRoomList();
        JSONArray res= new JSONArray();
        for (RoomEntity i:list){
            JSONObject data = new JSONObject();
            data.put("roomId",i.getRoomId());
            data.put("roomType", SwitchUtil.switchType(i.getRoomType()));
            data.put("paymentPerDay",SwitchUtil.switchTpyePayment(i.getRoomType()));
            res.add(data);
        }
        return res;
    }
    public int getMaxChangePage(){
        return (roomMapper.getEmptyRoomCount()+19) / 20;
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void updateStatus(){
        List<RecordEntity> list = recordMapper.getAll();
        for (RecordEntity i : list)
            if (i.getCheckOutTime().before( DateUtil.getNowDate())){
                i.setCheckOutTime(DateUtil.getNowDate());
                recordMapper.updateByPrimaryKey(i);
            }
        List<ReserveEntity> reserveList = reserveMapper.getAllByStatus(ConstPool.ACTIVE);
            //超出预定时间2天的自动取消
        for (ReserveEntity i:reserveList){
            if (DateUtil.subDateByDay(DateUtil.getNowDate(),i.getReserveCheckInTime())>2){
                i.setStatus(ConstPool.CANCEL);
                reserveMapper.updateByPrimaryKeySelective(i);
            }
        }
    }

}

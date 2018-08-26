package cn.xuzilin.controller;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.po.RecordEntity;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.service.RecordService;
import cn.xuzilin.service.ReserveService;
import cn.xuzilin.service.RoomService;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.JSONUtil;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class CommonControlller {
    @Resource
    private ReserveService reserveService;

    @Resource
    private RecordService recordService;

    @Resource
    private RoomService roomService;

    @PostMapping("/search")
    public MessageVo search(@RequestBody Map<String ,String> map){
        return ResponesUtil.success("success");
    }

    /**
     * 入住
     * @param map
     * @return
     */
    @PostMapping("/checkIn")
    public MessageVo checkIn(@RequestBody Map<String ,String> map){
        //接收参数
        String signIdcardNo = map.get("signIdcardNo");
        String signPhoneNum = map.get("signPhoneNum");
        String signName = map.get("signName");
        String signCheckOutTime = map.get("signCheckOutTime");
        String selectedRoomId =map.get("selectedRoomId");

        //首先判断预定信息
        if (!reserveService.canCheckIn(Integer.parseInt(selectedRoomId),DateUtil.getNowDate()))
            return ResponesUtil.systemError("该房间今日有预定信息，不能入住！如果是预定顾客入住请到预定管理页面办理入住");
        //入住
        RecordEntity record = new RecordEntity();
        record.setCustomerName(signName);
        record.setIdcardNo(signIdcardNo);
        record.setPhoneNum(signPhoneNum);
        record.setCheckOutTime(DateUtil.strToDate(signCheckOutTime));
        record.setCheckInTime(DateUtil.getNowDate());
        record.setStatus(ConstPool.CHECK_IN);
        record.setRoomId(Integer.parseInt(selectedRoomId));
        recordService.insert(record);
        //更新房间信息
        roomService.checkIn(Integer.parseInt(selectedRoomId));
        return ResponesUtil.success("success");
    }

    @PostMapping("/checkInReserve")
    public MessageVo checkInReserve(@RequestBody Map<String ,String> map){
        //接收参数
        String signIdcardNo = map.get("signIdcardNo");
        String signPhoneNum = map.get("signPhoneNum");
        String signName = map.get("signName");
        String signCheckOutTime = map.get("signCheckOutTime");
        String selectedRoomId =map.get("selectedRoomId");
        String id = map.get("selectedId");

        //入住
        RecordEntity record = new RecordEntity();
        record.setCustomerName(signName);
        record.setIdcardNo(signIdcardNo);
        record.setPhoneNum(signPhoneNum);
        record.setCheckOutTime(DateUtil.strToDate(signCheckOutTime));
        record.setCheckInTime(DateUtil.getNowDate());
        record.setStatus(ConstPool.CHECK_IN);
        record.setRoomId(Integer.parseInt(selectedRoomId));
        recordService.insert(record);
        //更新房间信息
        roomService.checkIn(Integer.parseInt(selectedRoomId));
        //更新预定信息
        reserveService.updateComplete(Integer.parseInt(id));
        return ResponesUtil.success("success");
    }


    /**
     * 续住
     * @param map
     * @return
     */
    @PostMapping("/delay")
    public MessageVo delay(@RequestBody Map<String ,String> map){
        String selectedRoomId =map.get("selectedRoomId");
        String delayCheckOutTime = map.get("delayCheckOutTime");
        boolean result;
        result = recordService.delay(Integer.parseInt(selectedRoomId),delayCheckOutTime);
        if (result)
            return ResponesUtil.success("success");
        return ResponesUtil.systemError("更新出错，请稍后重试");
    }

    /**
     * 退房
     * @param map
     * @return
     */
    @PostMapping("/checkOut")
    public MessageVo checkOut(@RequestBody Map<String ,String> map){
        String selectedRoomId =map.get("selectedRoomId");
        try {
            recordService.checkOut(Integer.parseInt(selectedRoomId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponesUtil.systemError("退房出错，请稍后重试");
        }
        return ResponesUtil.success("success");
    }

    /**
     * 换房
     * 入住roomId房间，退掉selectedRoomId房间
     * @param map
     * @return
     */
    @PostMapping("/changeRoom")
    public MessageVo changeRoom(@RequestBody Map<String ,String> map){
        String roomId = map.get("roomId");
        String selectedRoomId = map.get("selectedRoomId");
        recordService.changeRoom(Integer.parseInt(roomId),Integer.parseInt(selectedRoomId));
        return ResponesUtil.success("success");
    }

    /**
     * 获取首页数据
     * @param page
     * @return
     */
    @PostMapping("/dataList/{page}")
    public MessageVo dataList(@PathVariable ("page") int page,@RequestBody Map<String,String>map){
        String roomStatus = map.get("roomStatus");
        String checkInTime = map.get("checkInTime");
        String checkOutTime = map.get("checkOutTime");
        String roomId = map.get("roomId");
        String customerName = map.get("customerName");
        String customerIdNo = map.get("customerIdNo");

        JSONArray respData = roomService.getDataList(page,roomStatus,checkInTime,checkOutTime,roomId,customerName,customerIdNo);
        return ResponesUtil.success("success",respData);
    }

    /**
     * 获取可更换的房间列表
     * @param page
     * @return
     */
    @GetMapping("/changeRoomList/{page}")
    public MessageVo changeRoomList(@PathVariable("page")int page){
        JSONArray respData = roomService.getChangeRoomList(page);
        return ResponesUtil.success("success",respData);
    }

    /**
     * 获取可更换的房间页数
     * @return
     */
    @GetMapping("/getMaxChangePage")
    public MessageVo getMaxChangePage(){
        int respData = roomService.getMaxChangePage();
        return ResponesUtil.success("success",respData);
    }

    /**
     * 获取总页数
     * @return
     */
    @PostMapping("/getMaxPage")
    public MessageVo getMaxPage(@RequestBody Map<String,String>map){
        String roomStatus = map.get("roomStatus");
        String checkInTime = map.get("checkInTime");
        String checkOutTime = map.get("checkOutTime");
        String roomId = map.get("roomId");
        String customerName = map.get("customerName");
        String customerIdNo = map.get("customerIdNo");
        int respData = roomService.getMaxPage(roomStatus,checkInTime,checkOutTime,roomId,customerName,customerIdNo);
        return ResponesUtil.success("success",respData);
    }

    /**
     * 查询预定数目
     * @return
     */
    @GetMapping("/getReserveNum")
    public MessageVo getReserveNum(){
        int count = reserveService.getCount();
        return ResponesUtil.success("success",count);
    }

    @PostMapping("/checkOutInfo")
    public MessageVo checkOutInfo(@RequestBody Map<String,String>map){
        int roomId = Integer.parseInt(map.get("selectedRoomId"));
        JSONObject respData = recordService.getCheckOutInfo(roomId);
        return ResponesUtil.success("success",respData);
    }

    @GetMapping("/getAllEmptyRoom")
    public MessageVo getAllEmptyRoom(){
        return ResponesUtil.success("success");
    }
}

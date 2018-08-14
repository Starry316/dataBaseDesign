package cn.xuzilin.common.controller;

import cn.xuzilin.common.dao.ReserveEntityMapper;
import cn.xuzilin.common.dao.RoomEntityMapper;
import cn.xuzilin.common.po.ReserveEntity;
import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.service.ReserveService;
import cn.xuzilin.common.service.RoomService;
import cn.xuzilin.common.utils.DateUtil;
import cn.xuzilin.common.utils.JSONUtil;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.utils.SessionUtil;
import cn.xuzilin.common.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ReverseController {
    @Resource
    private ReserveService reserveService;
    @Resource
    private ReserveEntityMapper reserveMapper;
    private RoomService roomService;
    @Resource
    private RoomEntityMapper roomMapper;
    @GetMapping("/getReverseDataList/{page}")
    public MessageVo getReverseDataList(@PathVariable("page") int page){
        JSONArray respData = reserveService.getReserveDataList(page);
        return ResponesUtil.success("success",respData);
    }
    @GetMapping("/getReverseMaxPage")
    public MessageVo getReverseMaxPage(){
        int maxpage = (reserveService.getCount()+19)/20;
        return ResponesUtil.success("success",maxpage);
    }
    /**
     * 获取预定信息
     * @param map
     * @return
     */
    @PostMapping("/reserveInfo")
    public MessageVo reserveInfo(@RequestBody Map<String ,String> map){
        String selectedRoomId =map.get("selectedRoomId");
        ReserveEntity reserve = reserveService.getByRoomId(Integer.parseInt(selectedRoomId));
        JSONObject respData = new JSONObject();
        if (reserve == null){
            respData.put("isNull",true);
        }
        else respData = JSONUtil.toJSONObect(reserve);
        return ResponesUtil.success("success",respData);
    }
    @PostMapping("/cancelReserve")
    public MessageVo cancelReserve(@RequestBody Map<String ,String> map){
        String roomId = map.get("roomId");
        String id = map.get("id");
        reserveService.cancelReverse(Integer.parseInt(roomId),
                Integer.parseInt(id));
        return ResponesUtil.success("success");
    }
    @PostMapping("/reserve")
    public MessageVo reserve(HttpServletRequest request, @RequestBody Map<String , String> map){
         String name = map.get("name");
         String type = map.get("type");
         String telephone = map.get("telephone");
         String timeCheckIn = map.get("timeCheckIn");
         String timeCheckOut = map.get("timeCheckOut");
         Integer []roomIds = roomMapper.getRoomIdListByTypeAndCheckInTime(Byte.parseByte(type), DateUtil.strToDate(timeCheckIn));
         if (roomIds.length==0)
             return ResponesUtil.systemError("该房型已满，请预定别的房间");
         reserveService.reserve(roomIds[0],timeCheckIn,timeCheckOut,telephone,name);
         return ResponesUtil.success("success");
    }
}

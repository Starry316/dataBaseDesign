package cn.xuzilin.controller;

import cn.xuzilin.dao.ReserveEntityMapper;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.po.UserEntity;
import cn.xuzilin.service.ReserveService;
import cn.xuzilin.service.RoomService;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.JSONUtil;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.utils.SessionUtil;
import cn.xuzilin.vo.MessageVo;
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
        int maxpage = (reserveService.getCount()+14)/15;
        return ResponesUtil.success("success",maxpage);
    }
//    /**
//     * 获取预定信息
//     * @param map
//     * @return
//     */
//    @PostMapping("/reserveInfo")
//    public MessageVo reserveInfo(@RequestBody Map<String ,String> map){
//        String selectedRoomId =map.get("selectedRoomId");
//        ReserveEntity reserve = reserveService.getByRoomId(Integer.parseInt(selectedRoomId));
//        JSONObject respData = new JSONObject();
//        if (reserve == null){
//            respData.put("isNull",true);
//        }
//        else respData = JSONUtil.toJSONObect(reserve);
//        return ResponesUtil.success("success",respData);
//    }
    /**
     * 获取预定信息
     * @return
     */
    @GetMapping("/reserveInfo/{id}")
    public MessageVo reserveInfo(@PathVariable("id")int id){
        JSONObject respData = reserveService.getInfoById(id);
        if (respData == null){
            return ResponesUtil.systemError("出错，未查询到该预定记录！");
        }
        return ResponesUtil.success("success",respData);
    }
    @PostMapping("/cancelReserve")
    public MessageVo cancelReserve(@RequestBody Map<String ,String> map){
        String id = map.get("id");
        reserveService.cancelReverse(Integer.parseInt(id));
        return ResponesUtil.success("success");
    }
    @PostMapping("/reserve")
    public MessageVo reserve(HttpServletRequest request, @RequestBody Map<String , String> map){
         String name = map.get("name");
         String type = map.get("type");
         String telephone = map.get("telephone");
         String timeCheckIn = map.get("timeCheckin");
         String timeCheckOut = map.get("timeCheckOut");

         int userId = SessionUtil.get(request,"user",UserEntity.class).getId();
         Integer []roomIds = roomMapper.getRoomIdListByTypeAndCheckInTime(Byte.parseByte(type), DateUtil.strToDate(timeCheckIn),DateUtil.strToDate(timeCheckOut));
         if (roomIds.length==0)
             return ResponesUtil.systemError("该房型已满，请预定别的房间");
         reserveService.reserve(roomIds[0],timeCheckIn,timeCheckOut,telephone,name,userId);
         return ResponesUtil.success("success");
    }
    @GetMapping("/getHadReserveList")
    public MessageVo getHadReserveList(HttpServletRequest request){
        UserEntity user = SessionUtil.get(request,"user",UserEntity.class);
        JSONArray respData = reserveService.getAllActive(user.getId());
        return ResponesUtil.success("success",respData);
    }
}

package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.ReserveEntity;
import cn.xuzilin.common.service.ReserveService;
import cn.xuzilin.common.utils.JSONUtil;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class ReverseController {
    @Resource
    private ReserveService reserveService;
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
        reserveService.cancelReverse(Integer.parseInt(roomId));
        return ResponesUtil.success("success");
    }
}

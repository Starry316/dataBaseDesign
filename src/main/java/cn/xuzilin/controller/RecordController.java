package cn.xuzilin.controller;

import cn.xuzilin.service.RecordService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class RecordController {

    @Resource
    private RecordService recordService;

    @PostMapping("/recordDataList")
    public MessageVo getRecordDataList(@RequestBody Map<String,String> map){
        String page = map.get("page");
        String roomId = map.get("roomId");
        String customerName = map.get("customerName");
        String checkInTime = map.get("checkInTime");
        String checkOutTime = map.get("checkOutTime");
        JSONArray respData = recordService.getCompleteRecord(page,roomId,checkInTime,checkOutTime,customerName);
        return ResponesUtil.success("success",respData);

    }

    @PostMapping("/getRecordMaxPage")
    public MessageVo getRecordMaxPage(@RequestBody Map<String ,String >map){
        String roomId = map.get("roomId");
        String customerName = map.get("customerName");
        String checkInTime = map.get("checkInTime");
        String checkOutTime = map.get("checkOutTime");
        int maxPage = recordService.getRecordMaxPage(roomId,checkInTime,checkOutTime,customerName);
        return ResponesUtil.success("success",maxPage);
    }
}

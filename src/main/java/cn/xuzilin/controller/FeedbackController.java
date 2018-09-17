package cn.xuzilin.controller;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.service.FeedbackService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class FeedbackController {
    @Resource
    FeedbackService feedbackService;

    @GetMapping("/receviceFeedback/{id}")
    public MessageVo receviceFeedBack(@PathVariable("id") int id){
        feedbackService.updateStatusById(id, ConstPool.FEEDBACK_DEALING);
        return ResponesUtil.success("success");

    }
    @GetMapping("/completeFeedback/{id}")
    public MessageVo completeFeedBack(@PathVariable("id") int id){
        feedbackService.updateStatusById(id, ConstPool.FEEDBACK_COMPLETE);
        return ResponesUtil.success("success");

    }

    @GetMapping("/getFeedbackContent/{id}")
    public MessageVo getFeedbackContent(@PathVariable("id") int id){
        String respData = feedbackService.getContentById(id);
        return ResponesUtil.success("success",respData);
    }


    @PostMapping("/feedbackDataList")
    public MessageVo feedbackDataList(@RequestBody Map<String,String>map){
        String page = map.get("page");
        String dealStatus = map.get("dealStatus");
        JSONArray respData = feedbackService.getData(Integer.parseInt(page),Byte.parseByte(dealStatus));
        return ResponesUtil.success("success",respData);
    }
    @PostMapping("/getFeedbackMaxPage")
    public MessageVo getFeedbackMaxPage(@RequestBody Map<String,String>map){
        //String page = map.get("page");
        String dealStatus = map.get("dealStatus");
        int maxPage = feedbackService.getMaxPage(Byte.parseByte(dealStatus));
        return ResponesUtil.success("success",maxPage);
    }

}

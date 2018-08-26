package cn.xuzilin.controller;

import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Starry on 2018/7/15.
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/testParam")
    public MessageVo testParam(@RequestParam Map<String , String > map){
        map.get("a2");
        return ResponesUtil.success("success", map.get("a1"));

    }


}

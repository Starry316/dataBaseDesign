package cn.xuzilin.controller;

import cn.xuzilin.service.FeedbackService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeedbackController {
    @Resource
    FeedbackService feedbackService;

}

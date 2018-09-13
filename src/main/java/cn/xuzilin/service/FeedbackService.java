package cn.xuzilin.service;

import cn.xuzilin.dao.FeedBackEntityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackService {

    @Resource
    private FeedBackEntityMapper feedBackMapper;

}

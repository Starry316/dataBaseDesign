package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.FeedBackEntityMapper;
import cn.xuzilin.po.FeedBackEntity;
import cn.xuzilin.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackService {

    @Resource
    private FeedBackEntityMapper feedBackMapper;

    public int createFeedback(int userId , String content){
        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setContent(content);
        feedBackEntity.setSubmitTime(DateUtil.getNowDate());
        feedBackEntity.setUserId(userId);
        feedBackEntity.setStatus(ConstPool.FEEDBACK_SUBMITED);
        return feedBackMapper.insertSelective(feedBackEntity);
    }

    public int updateStatusDealTimeById(int id ,byte status,int managerId){
        return feedBackMapper.updateStatusDealTimeById(status,id,managerId,DateUtil.getNowDate());
    }
    public int updateStatusById(int id ,byte status){
        return feedBackMapper.updateStatusById(status,id);
    }
    public int bindFeedbackManager(int id ,int managerId){
        return feedBackMapper.updateManagerById(managerId,id);
    }

    public String getContentById(int id){
        return feedBackMapper.getContentById(id);
    }

    public JSONArray getData(int page ,byte status){
        PageHelper.startPage(page,15);
        JSONArray data = JSON.parseArray(JSON.toJSONString(feedBackMapper.getData(status)));
        return data;
    }

    public int getMaxPage(byte status){
        int count = feedBackMapper.getCount(status);
        return (count+14)/15;
    }

}

package cn.xuzilin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Starry on 2018/7/25.
 */
public class JSONUtil {

    public static <K> JSONArray toJSONArray(List<K> list){
        String jsonString = JSON.toJSONString(list);
        return JSON.parseArray(jsonString);
    }

    public static JSONObject toJSONObect(Object o){
        return JSON.parseObject(JSON.toJSONString(o));
    }
}

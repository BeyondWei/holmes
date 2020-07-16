package com.shuzheng.holmes.core.filter;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class ToJsonFilter extends HolmesFilterAbstract {

    @Override
    protected Object filter(Object msg) {
        String split = configContext.getString("split");
        String equal = configContext.getString("equal");
        HashMap<String, String> hashMap = new HashMap<>();
        Arrays.asList(msg.toString().split(split)).forEach(m -> {
            String key = "异常";
            String value = "异常";
            try {
                key = m.split(equal)[0];
                value = m.split(equal)[1];
                hashMap.put(key, value);
            } catch (Exception e) {
                hashMap.put(key, value);
            }
        });
        return JSONObject.toJSONString(hashMap);
    }
}

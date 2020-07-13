package com.shuzheng.holmes.core.filter;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class JsonFilter extends HolmesFilterAbstract {

    @Override
    protected Object filter(Object msg) {
        String keys = configContext.getString("keys");
        HashMap<String, String> hashMap = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(msg.toString());
        Arrays.asList(keys.split(",")).forEach(key -> {
            hashMap.put(key, jsonObject.getString(key));
        });
        return hashMap;
    }
}

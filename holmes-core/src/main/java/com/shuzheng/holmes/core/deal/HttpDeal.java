package com.shuzheng.holmes.core.deal;

import com.alibaba.fastjson.JSONObject;
import com.shuzheng.holmes.common.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpDeal extends HolmesDealAbstract {

    @Override
    public void deal(Object msg) {
        String url = configContext.getString("url");
        String key = configContext.getString("key");
        String heads = configContext.getString("heads");
        String forms = configContext.getString("forms");
        Map headMap = JSONObject.parseObject(heads, Map.class);
        Map other = JSONObject.parseObject(forms, Map.class);
        String type = configContext.getString("type");
        if (other == null) {
            other = new HashMap();
        }
        other.put(key, msg.toString());
        if ("get".equalsIgnoreCase(type)) {
            HttpClientUtil.sendGet(url + key + "=" + msg.toString(), headMap);
        } else {
            HttpClientUtil.sendPostForm(url, other, headMap);
        }
    }

}

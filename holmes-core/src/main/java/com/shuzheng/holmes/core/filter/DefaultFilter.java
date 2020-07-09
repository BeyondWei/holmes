package com.shuzheng.holmes.core.filter;

import com.alibaba.fastjson.JSONObject;

public class DefaultFilter extends HolmesFilterAbstract{

    @Override
    public Object filter(Object msg) {
        return msg;
    }
}

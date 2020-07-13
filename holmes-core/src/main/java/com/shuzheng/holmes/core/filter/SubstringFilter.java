package com.shuzheng.holmes.core.filter;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;

public class SubstringFilter extends HolmesFilterAbstract {

    @Override
    protected Object filter(Object msg) {
        HashMap hashMap = new HashMap<>();
        ImmutableMap<String, String> parameters = configContext.getParameters();
        parameters.forEach((key, value) -> {
            int begging = Integer.parseInt(value.split(",")[0]);
            int ending = Integer.parseInt(value.split(",")[1]);
            String m;
            try {
                m = msg.toString().substring(begging, ending);
            } catch (Exception e) {
                m = "数组越界：" + value;
            }
            hashMap.put(key, m);
        });
        return hashMap;
    }
}

package com.shuzheng.holmes.core.filter;

import java.util.Arrays;
import java.util.HashMap;

public class SplitFilter extends HolmesFilterAbstract {

    @Override
    protected Object filter(Object msg) {
        String splitKey = configContext.getString("splitKey");
        String value = configContext.getString("value");
        String[] split = msg.toString().split(splitKey);
        HashMap<String, String> hashMap = new HashMap<>();
        Arrays.asList(value.split(",")).forEach(nub -> {
                    String res = "";
                    try {
                        res = split[Integer.parseInt(nub)];
                    } catch (Exception e) {
                        res = "字符串分隔异常";
                    }
                    hashMap.put(nub, res);
                }
        );
        return hashMap;
    }
}

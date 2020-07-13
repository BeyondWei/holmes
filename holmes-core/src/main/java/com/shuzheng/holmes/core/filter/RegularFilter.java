package com.shuzheng.holmes.core.filter;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularFilter extends HolmesFilterAbstract {

    /**
     * 编译一个正则表达式
     *
     * @param regex
     * @return
     */
    public static Pattern compile(String regex, boolean isInsensitive) {
        if (isInsensitive) {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            return Pattern.compile(regex);
        }
    }

    /**
     * 返回一个mathcer
     *
     * @param regex
     * @param content
     * @return
     */
    public static Matcher matcher(String regex, String content) {
        return compile(regex, true).matcher(content);
    }

    /**
     * 正则表达式获取第一个符合的字符串
     *
     * @param msg
     * @return
     */
    @Override
    protected Object filter(Object msg) {
        HashMap hashMap = new HashMap<>();
        ImmutableMap<String, String> parameters = configContext.getParameters();
        parameters.forEach((key, value) -> {
            Matcher m = matcher(value, msg.toString());
            while (m.find()) {
                hashMap.put(key, m.group().trim());
                break;
            }
        });
        return hashMap;
    }

}

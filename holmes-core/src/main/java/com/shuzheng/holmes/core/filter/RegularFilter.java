package com.shuzheng.holmes.core.filter;

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
        if (true == isInsensitive) {
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
     * @param msg
     * @return
     */
    @Override
    public Object filter(Object msg) {
        String regular = getConfigContext().getString("regular");

        String match = null;
        Matcher m = matcher(regular, msg.toString());
        while (m.find()) {
            match = m.group().trim();
            break;
        }
        return match;
    }

}

package com.shuzheng.holmes.demo;

import com.shuzheng.holmes.core.filter.HolmesFilterAbstract;

public class NginxFilter extends HolmesFilterAbstract {

    @Override
    public Object filter(Object msg) {
        System.out.println(msg);
        return null;
    }

}

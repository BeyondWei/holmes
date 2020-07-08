package com.shuzheng.holmes.core.filter;

public class DefaultFilter extends HolmesFilterAbstract{

    @Override
    public Object filter(Object msg) {
        return msg;
    }
}

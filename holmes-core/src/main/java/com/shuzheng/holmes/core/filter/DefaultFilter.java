package com.shuzheng.holmes.core.filter;

public class DefaultFilter extends HolmesFilterAbstract{

    @Override
    protected Object filter(Object msg) {
        return msg;
    }
}
